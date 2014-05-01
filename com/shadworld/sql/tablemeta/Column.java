/*     */ package com.shadworld.sql.tablemeta;
/*     */ 
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import java.sql.Timestamp;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ 
/*     */ public class Column
/*     */ {
/*  17 */   private Class javaClass = String.class;
/*  18 */   private SqlType sqlType = SqlType.VARCHAR;
/*  19 */   private int length = 255;
/*     */   private String dbColName;
/*     */   private Object recKey;
/*  22 */   private boolean notNull = false;
/*     */   private Object defaultValue;
/*  24 */   private boolean unsigned = true;
/*  25 */   private boolean binary = false;
/*  26 */   private boolean zerofill = false;
/*     */   private DateFormat dateFormat;
/*     */ 
/*     */   public static Column PRIMARY_KEY(String tableName)
/*     */   {
/*  31 */     Column pk = new Column(SqlType.INTEGER, 0, tableName, true, null);
/*  32 */     pk.javaClass = Integer.class;
/*  33 */     return pk;
/*     */   }
/*     */ 
/*     */   public String buildCreate(boolean primaryKey) {
/*  37 */     StringBuilder sb = new StringBuilder("\t");
/*  38 */     sb.append(StringTools.wrap(this.dbColName, '`')).append(" ");
/*  39 */     sb.append(this.sqlType.toString());
/*  40 */     if ((this.sqlType.hasLength) && (this.length != -1))
/*  41 */       sb.append("(").append(this.length).append(")");
/*  42 */     if ((this.sqlType.canBeSigned) && (this.unsigned))
/*  43 */       sb.append(" UNSIGNED");
/*  44 */     if ((this.sqlType.canBeZeroFilled) && (this.zerofill))
/*  45 */       sb.append(" ZEROFILL");
/*  46 */     if (this.notNull)
/*  47 */       sb.append(" NOT NULL");
/*  48 */     if ((primaryKey) && (this.sqlType.canAutoIncrement))
/*  49 */       sb.append(" AUTO_INCREMENT");
/*  50 */     if (this.defaultValue != null)
/*  51 */       sb.append(" DEFAULT ").append(StringTools.wrap(this.defaultValue.toString(), "'"));
/*  52 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public Column(SqlType sqlType, int length, String dbColName, boolean notNull, Object defaultValue)
/*     */   {
/*  57 */     this.sqlType = sqlType;
/*  58 */     this.length = length;
/*  59 */     this.dbColName = dbColName;
/*  60 */     this.notNull = notNull;
/*  61 */     this.defaultValue = defaultValue;
/*     */   }
/*     */ 
/*     */   private java.util.Date getAsDate(Object o) {
/*  65 */     java.util.Date date = null;
/*     */ 
/*  67 */     if ((o instanceof java.util.Date))
/*  68 */       date = (java.util.Date)o;
/*  69 */     else if ((o instanceof Calendar))
/*  70 */       date = ((Calendar)o).getTime();
/*  71 */     else if ((o instanceof Long))
/*  72 */       date = new java.util.Date(((Long)o).longValue());
/*  73 */     if ((date == null) && (this.dateFormat != null)) {
/*     */       try {
/*  75 */         date = o == null ? null : this.dateFormat.parse(o.toString());
/*     */       } catch (ParseException e) {
/*  77 */         e.printStackTrace();
/*     */       }
/*     */     }
/*  80 */     return date;
/*     */   }
/*     */ 
/*     */   private String formatDate(java.util.Date date) {
/*  84 */     if (date != null) {
/*  85 */       DateFormat df = null;
/*  86 */       if (this.sqlType == SqlType.DATETIME)
/*  87 */         df = com.shadworld.util.Time.sqlDateTimeFormat;
/*  88 */       else if (this.sqlType == SqlType.DATE)
/*  89 */         df = com.shadworld.util.Time.sqlDateFormat;
/*  90 */       else if (this.sqlType == SqlType.TIME)
/*  91 */         df = com.shadworld.util.Time.sqlTimeFormat;
/*  92 */       if (df != null)
/*  93 */         return df.format(date);
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getSqlValue(Object o) {
/*  99 */     if (!SqlType.needsModification(this.sqlType))
/* 100 */       return o;
/* 101 */     if (SqlType.isDateType(this.sqlType)) {
/* 102 */       java.util.Date date = getAsDate(o);
/* 103 */       return formatDate(date);
/* 104 */     }if (this.sqlType == SqlType.TIMESTAMP) {
/* 105 */       java.util.Date date = getAsDate(o);
/* 106 */       return date == null ? null : Long.valueOf(date.getTime() / 1000L);
/*     */     }
/* 108 */     if (this.sqlType == SqlType.BOOLEAN) {
/* 109 */       Boolean bool = null;
/* 110 */       if ((o instanceof Boolean))
/* 111 */         bool = (Boolean)o;
/* 112 */       else if ((o instanceof Number))
/* 113 */         bool = o == null ? null : Boolean.valueOf(((Number)o).intValue() != 0);
/* 114 */       else if ((o instanceof CharSequence)) {
/* 115 */         bool = o == null ? null : 
/* 116 */           Boolean.valueOf(o.toString().equalsIgnoreCase("true") ? true : 
/* 117 */           (o.toString().equalsIgnoreCase("false") ? Boolean.valueOf(false) : null).booleanValue());
/*     */       }
/* 119 */       return bool == null ? null : Record.BooleanAsInt(bool);
/*     */     }
/* 121 */     return o;
/*     */   }
/*     */ 
/*     */   public Class getJavaClass()
/*     */   {
/* 183 */     return this.javaClass;
/*     */   }
/*     */   public void setJavaClass(Class javaClass) {
/* 186 */     this.javaClass = javaClass;
/*     */   }
/*     */   public int getLength() {
/* 189 */     return this.length;
/*     */   }
/*     */   public void setLength(int length) {
/* 192 */     this.length = length;
/*     */   }
/*     */   public String getDbColName() {
/* 195 */     return this.dbColName;
/*     */   }
/*     */   public void setDbColName(String dbColName) {
/* 198 */     this.dbColName = dbColName;
/*     */   }
/*     */   public Object getRecKey() {
/* 201 */     return this.recKey;
/*     */   }
/*     */   public void setRecKey(Object recKey) {
/* 204 */     this.recKey = recKey;
/*     */   }
/*     */   public boolean isNotNull() {
/* 207 */     return this.notNull;
/*     */   }
/*     */   public void setNotNull(boolean notNull) {
/* 210 */     this.notNull = notNull;
/*     */   }
/*     */   public Object getDefaultValue() {
/* 213 */     return this.defaultValue;
/*     */   }
/*     */   public void setDefaultValue(Object defaultValue) {
/* 216 */     this.defaultValue = defaultValue;
/*     */   }
/*     */ 
/*     */   public static enum SqlType
/*     */   {
/* 126 */     INTEGER(Integer.class, true, false, true, true, new Class[] { Integer.class, Short.class, Byte.class, Long.class }), 
/* 127 */     BIGINT(Long.class, true, false, true, true, new Class[] { Integer.class, Short.class, Byte.class, Long.class }), 
/* 128 */     MEDIUMINT(Short.class, true, false, true, true, new Class[] { Short.class, Byte.class }), 
/* 129 */     DOUBLE(Double.class, false, false, true, true, new Class[] { Double.class, Number.class }), 
/* 130 */     FLOAT(Float.class, false, false, true, true, new Class[] { Float.class, Integer.class, Short.class, Byte.class }), 
/* 131 */     DATETIME(java.util.Date.class, false, false, false, false, new Class[] { java.util.Date.class, Calendar.class, java.sql.Date.class, java.sql.Time.class }), 
/* 132 */     DATE(java.sql.Date.class, false, false, false, false, new Class[] { java.util.Date.class, Calendar.class, java.sql.Date.class }), 
/* 133 */     TIME(java.sql.Time.class, false, false, false, false, new Class[] { java.util.Date.class, Calendar.class, java.sql.Time.class }), 
/* 134 */     TIMESTAMP(Timestamp.class, false, false, false, false, new Class[] { java.util.Date.class, Calendar.class, Long.class, Timestamp.class }), 
/* 135 */     BOOLEAN(Boolean.class, false, false, false, true, new Class[0]), 
/* 136 */     VARCHAR(String.class, false, true, false, false, new Class[] { String.class, CharSequence.class, Object.class }), 
/* 137 */     TEXT(null, false, false, false, false, new Class[] { String.class, CharSequence.class, Object.class });
/*     */ 
/*     */     private Class[] classes;
/*     */     private Class defaultClass;
/*     */     private boolean hasLength;
/*     */     private boolean canBeSigned;
/*     */     private boolean canBeZeroFilled;
/*     */     private boolean canAutoIncrement;
/*     */ 
/* 141 */     public static boolean needsModification(SqlType t) { return (t == DATETIME) || (t == BOOLEAN) || (t == DATE) || (t == TIME) || (t == TIMESTAMP); }
/*     */ 
/*     */     public static boolean isDateType(SqlType t)
/*     */     {
/* 145 */       return (t == DATETIME) || (t == DATE) || (t == TIME);
/*     */     }
/*     */ 
/*     */     public static SqlType getDefaultType(Class c, int length) {
/* 149 */       SqlType type = null;
/* 150 */       for (SqlType t : values()) {
/* 151 */         if ((c == t.defaultClass) && (t.defaultClass != null)) {
/* 152 */           type = t;
/* 153 */           break;
/*     */         }
/*     */       }
/* 156 */       if (type == null)
/* 157 */         type = VARCHAR;
/* 158 */       if ((type == VARCHAR) && ((length <= 0) || (length > 1024)))
/* 159 */         type = TEXT;
/* 160 */       return type;
/*     */     }
/*     */ 
/*     */     private SqlType(Class defaultClass, boolean canAutoIncrement, boolean hasLength, boolean canBeSigned, boolean canBeZeroFilled, Class[] classes)
/*     */     {
/* 170 */       this.classes = classes;
/* 171 */       this.defaultClass = defaultClass;
/* 172 */       this.canAutoIncrement = canAutoIncrement;
/* 173 */       this.hasLength = hasLength;
/* 174 */       this.canBeSigned = canBeSigned;
/* 175 */       this.canBeZeroFilled = canBeZeroFilled;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.tablemeta.Column
 * JD-Core Version:    0.6.2
 */