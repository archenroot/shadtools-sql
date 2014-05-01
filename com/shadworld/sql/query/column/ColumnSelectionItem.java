/*     */ package com.shadworld.sql.query.column;
/*     */ 
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ColumnSelectionItem<C>
/*     */ {
/*     */   private String columnName;
/*     */   private PropertyDescriptor prop;
/*     */   private IValueModel valueModel;
/*     */ 
/*     */   public ColumnSelectionItem(String columnName, IValueModel valueModel)
/*     */   {
/*  19 */     this.columnName = columnName;
/*     */ 
/*  21 */     this.valueModel = valueModel;
/*     */   }
/*     */ 
/*     */   public ColumnSelectionItem(String columnName)
/*     */   {
/*  26 */     this.columnName = columnName;
/*     */   }
/*     */ 
/*     */   public ColumnSelectionItem(String columnName, PropertyDescriptor prop)
/*     */   {
/*  32 */     this.columnName = columnName;
/*  33 */     this.valueModel = new BeanValueModel(prop);
/*     */   }
/*     */ 
/*     */   public ColumnSelectionItem(String columnName, String columnKey, Map map) {
/*  37 */     this(columnName, new MapValueModel(columnKey));
/*     */   }
/*     */ 
/*     */   public ColumnSelectionItem(String columnName, String literalString)
/*     */   {
/*  49 */     this(columnName, new SqlLiteralValueModel(literalString));
/*     */   }
/*     */ 
/*     */   public ColumnSelectionItem(String columnName, String value, Object anyRubbish)
/*     */   {
/*  63 */     this(columnName, new ValueModel(value));
/*     */   }
/*     */ 
/*     */   public Object getValue(int row, int col, Object target)
/*     */   {
/*  77 */     if (this.valueModel == null)
/*  78 */       return null;
/*  79 */     return this.valueModel.getValue(row, col, target);
/*     */   }
/*     */ 
/*     */   public Object getValue(int row, Object target)
/*     */   {
/*  92 */     if (this.valueModel == null)
/*  93 */       return null;
/*  94 */     return this.valueModel.getValue(row, target);
/*     */   }
/*     */ 
/*     */   public Object getValue(Object target)
/*     */   {
/* 106 */     if (this.valueModel == null)
/* 107 */       return null;
/* 108 */     return this.valueModel.getValue(target);
/*     */   }
/*     */ 
/*     */   public boolean isLastValueLiteral()
/*     */   {
/* 116 */     return this.valueModel.isLastValueLiteral();
/*     */   }
/*     */ 
/*     */   public String getValueString(int row, int col, Object target)
/*     */   {
/* 130 */     if (this.valueModel == null)
/* 131 */       return "null";
/* 132 */     return String.valueOf(this.valueModel.getValue(row, col, target));
/*     */   }
/*     */ 
/*     */   public String getValueString(int row, Object target)
/*     */   {
/* 145 */     if (this.valueModel == null)
/* 146 */       return "null";
/* 147 */     return String.valueOf(this.valueModel.getValue(row, target));
/*     */   }
/*     */ 
/*     */   public String getValueString(Object target)
/*     */   {
/* 161 */     if (this.valueModel == null)
/* 162 */       return "null";
/* 163 */     return String.valueOf(this.valueModel.getValue(target));
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public boolean isLiteral()
/*     */   {
/* 172 */     return this.valueModel instanceof SqlLiteralValueModel;
/*     */   }
/*     */ 
/*     */   public String getColumnName()
/*     */   {
/* 179 */     return this.columnName;
/*     */   }
/*     */ 
/*     */   public void setColumnName(String columnName)
/*     */   {
/* 187 */     this.columnName = columnName;
/*     */   }
/*     */ 
/*     */   public IValueModel<C> getValueModel()
/*     */   {
/* 194 */     return this.valueModel;
/*     */   }
/*     */ 
/*     */   public void setValueModel(IValueModel<C> valueModel)
/*     */   {
/* 202 */     this.valueModel = valueModel;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.ColumnSelectionItem
 * JD-Core Version:    0.6.2
 */