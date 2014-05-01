/*     */ package com.shadworld.sql.tablemeta;
/*     */ 
/*     */ import com.shadworld.sql.orm.BeanRegistry;
/*     */ import com.shadworld.sql.orm.JPAUtil;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.persistence.Transient;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ 
/*     */ public class TableMeta
/*     */ {
/*  25 */   private Set<Column> columns = new LinkedHashSet();
/*  26 */   private Set<Index> indexes = new LinkedHashSet();
/*     */   private Index primaryKey;
/*     */   private String tableName;
/*     */   private String dbName;
/*  30 */   private Engine engine = Engine.MyISAM;
/*  31 */   private Charset charset = Charset.utf8;
/*  32 */   private Collation collation = Collation.utf8_general_ci;
/*     */ 
/*     */   public TableMeta(String dbName, String tableName)
/*     */   {
/*  39 */     this.tableName = tableName;
/*  40 */     this.dbName = dbName;
/*     */   }
/*     */ 
/*     */   public TableMeta(String tableName)
/*     */   {
/*  45 */     this.tableName = tableName;
/*     */   }
/*     */ 
/*     */   public Column getColumn(Object key) {
/*  49 */     for (Column col : this.columns) {
/*  50 */       Object recKey = col.getRecKey();
/*  51 */       if ((key == recKey) || ((key != null) && (key.equals(recKey))))
/*  52 */         return col;
/*     */     }
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public TableMeta addColumn(Column col) {
/*  58 */     this.columns.add(col);
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public TableMeta addColumns(Column[] cols) {
/*  63 */     for (Column col : cols)
/*  64 */       this.columns.add(col);
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public TableMeta addIndex(Index index) {
/*  69 */     this.indexes.add(index);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public TableMeta addIndex(Index[] indexes) {
/*  74 */     for (Index index : indexes)
/*  75 */       this.indexes.add(index);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public TableMeta addPrimaryKey(Index pk) {
/*  80 */     if ((pk == null) || (pk.type != Index.IndexType.PRIMARY))
/*  81 */       throw new IllegalArgumentException("tried to add primary key but is not IndexType.PRIMARY");
/*  82 */     this.primaryKey = pk;
/*  83 */     this.indexes.add(pk);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public String buildCreate(boolean createIfNotExists) {
/*  88 */     StringBuilder sb = new StringBuilder("/* TableMeta generated create statement */\n\n");
/*  89 */     sb.append("CREATE TABLE ");
/*  90 */     if (createIfNotExists)
/*  91 */       sb.append("IF NOT EXISTS ");
/*  92 */     if (this.dbName != null)
/*  93 */       sb.append(StringTools.wrap(this.dbName, '`')).append('.');
/*  94 */     sb.append(StringTools.wrap(this.tableName, '`'));
/*  95 */     sb.append(" (\n");
/*  96 */     for (Iterator i = this.columns.iterator(); i.hasNext(); ) {
/*  97 */       Column col = (Column)i.next();
/*  98 */       sb.append(col.buildCreate(isPrimaryKey(col)));
/*  99 */       if ((i.hasNext()) || (this.indexes.size() > 0))
/* 100 */         sb.append(",");
/* 101 */       sb.append("\n");
/*     */     }
/* 103 */     for (Iterator i = this.indexes.iterator(); i.hasNext(); ) {
/* 104 */       Index index = (Index)i.next();
/* 105 */       sb.append(index.buildCreate());
/* 106 */       if (i.hasNext())
/* 107 */         sb.append(",");
/* 108 */       sb.append("\n");
/*     */     }
/* 110 */     sb.append(")\n");
/* 111 */     sb.append("ENGINE = ").append(this.engine.toString()).append("\n");
/* 112 */     sb.append("CHARACTER SET ").append(this.charset.toString());
/* 113 */     sb.append(" COLLATE ").append(this.collation.toString());
/* 114 */     sb.append(";");
/* 115 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public boolean isPrimaryKey(Column col) {
/* 119 */     return (this.primaryKey != null) && (this.primaryKey.columns.contains(col));
/*     */   }
/*     */ 
/*     */   public static TableMeta createFromDataTable(DataTable tbl, String tableName)
/*     */   {
/* 139 */     return createFromDataTable(tbl, null, tableName, new String[0]);
/*     */   }
/*     */ 
/*     */   public static TableMeta createFromDataTable(DataTable tbl, String dbName, String tableName, String[] pkCols)
/*     */   {
/* 144 */     Record classRec = tbl.ensureConsistentColumns(null);
/* 145 */     Record lengthRec = calcColumnMaxLength(tbl, classRec);
/* 146 */     return createFromMeta(classRec, lengthRec, dbName, tableName, pkCols);
/*     */   }
/*     */ 
/*     */   public static TableMeta createFromBeanClass(Class c, String dbName, String tableName, String[] pkCols)
/*     */   {
/* 161 */     Record classRec = new Record();
/* 162 */     Record lengthRec = new Record();
/* 163 */     PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(c);
/* 164 */     for (PropertyDescriptor prop : props) {
/* 165 */       if ((prop.getReadMethod() != null) && 
/* 166 */         (!prop.getName().equalsIgnoreCase("class")) && 
/* 167 */         (!Collection.class.isAssignableFrom(prop.getPropertyType())) && 
/* 168 */         (JPAUtil.findAnnotationForProperty(Transient.class, c, prop) == null)) {
/* 169 */         String colName = BeanRegistry.get().getPropertyColumnName(c, prop);
/* 170 */         classRec.put(colName, prop.getPropertyType());
/* 171 */         int length = 0;
/*     */ 
/* 174 */         if (CharSequence.class.isAssignableFrom(prop.getPropertyType())) {
/* 175 */           javax.persistence.Column col = 
/* 176 */             (javax.persistence.Column)JPAUtil.findAnnotationForProperty(javax.persistence.Column.class, c, prop);
/* 177 */           if (col != null)
/* 178 */             length = col.length();
/*     */           else {
/* 180 */             length = 255;
/*     */           }
/*     */         }
/* 183 */         lengthRec.put(colName, Integer.valueOf(length));
/*     */       }
/*     */     }
/*     */ 
/* 187 */     return createFromMeta(classRec, lengthRec, dbName, tableName, pkCols);
/*     */   }
/*     */ 
/*     */   private static Record calcColumnMaxLength(DataTable tbl, Record classRec) {
/* 191 */     Record len = new Record();
/* 192 */     for (Iterator localIterator = classRec.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 193 */       len.put(key, Integer.valueOf(maxLength(tbl.getColumnValues(key))));
/*     */     }
/* 195 */     return len;
/*     */   }
/*     */ 
/*     */   public static TableMeta createFromMeta(Record classRec, Record lengthRec, String dbName, String tableName, String[] pkCols)
/*     */   {
/* 210 */     TableMeta meta = new TableMeta(dbName, tableName);
/*     */ 
/* 212 */     if ((pkCols == null) || (pkCols.length == 0)) {
/* 213 */       if (classRec.containsKey("id")) {
/* 214 */         pkCols = new String[] { "id" };
/*     */       } else {
/* 216 */         Column pkCol = Column.PRIMARY_KEY("id");
/* 217 */         meta.addColumn(pkCol);
/* 218 */         meta.addPrimaryKey(Index.PRIMARY_KEY(new Column[] { pkCol }));
/*     */       }
/*     */     }
/*     */ 
/* 222 */     for (Iterator localIterator = classRec.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 223 */       Class c = null;
/* 224 */       Object classVal = classRec.get(key);
/* 225 */       if ((classVal != null) && (!(classVal instanceof Class)))
/* 226 */         c = classVal.getClass();
/*     */       else
/* 228 */         c = (Class)classVal;
/* 229 */       int length = 0;
/* 230 */       if ((c == null) || (CharSequence.class.isAssignableFrom(c))) {
/* 231 */         length = lengthRec.getInteger(key).intValue();
/* 232 */         if (length <= 0)
/* 233 */           length = 255;
/*     */       }
/* 235 */       Column col = new Column(Column.SqlType.getDefaultType(c, length), 
/* 236 */         length, 
/* 237 */         key.toString().toLowerCase(), 
/* 238 */         false, null);
/* 239 */       col.setJavaClass(c);
/* 240 */       col.setRecKey(key);
/* 241 */       meta.addColumn(col);
/*     */     }
/*     */ 
/* 244 */     if ((pkCols != null) && (pkCols.length > 0)) {
/* 245 */       List pkColList = new ArrayList();
/* 246 */       for (int i = 0; i < pkCols.length; i++) {
/* 247 */         Column c = meta.getColumn(pkCols[i]);
/* 248 */         if (c != null)
/* 249 */           pkColList.add(c);
/*     */       }
/* 251 */       Index pk = Index.PRIMARY_KEY((Column[])pkColList.toArray(new Column[pkColList.size()]));
/* 252 */       meta.addPrimaryKey(pk);
/*     */     }
/*     */ 
/* 255 */     return meta;
/*     */   }
/*     */ 
/*     */   private static int maxLength(List<Object> list) {
/* 259 */     int max = 0;
/* 260 */     for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
/* 261 */       if (o != null) {
/* 262 */         int len = o.toString().length();
/* 263 */         if (len > max)
/* 264 */           max = len;
/*     */       }
/*     */     }
/* 267 */     return max;
/*     */   }
/*     */ 
/*     */   public static enum Charset
/*     */   {
/* 123 */     Default, utf8;
/*     */   }
/*     */ 
/*     */   public static enum Collation {
/* 127 */     utf8_general_ci, utf8_bin, utf8_unicode_ci;
/*     */   }
/*     */ 
/*     */   public static enum Engine {
/* 131 */     MyISAM, InnoDB, MEMORY, BLACKHOLE, MRG_MYISAM, CSV, ARCHIVE;
/*     */ 
/*     */     public static Engine getDefault() {
/* 134 */       return MyISAM;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.tablemeta.TableMeta
 * JD-Core Version:    0.6.2
 */