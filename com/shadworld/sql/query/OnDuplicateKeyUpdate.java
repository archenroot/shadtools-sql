/*     */ package com.shadworld.sql.query;
/*     */ 
/*     */ import com.shadworld.sql.query.column.ColumnSelection;
/*     */ import com.shadworld.sql.query.column.IValueModel;
/*     */ import com.shadworld.sql.query.column.ValueModel;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class OnDuplicateKeyUpdate extends Q<OnDuplicateKeyUpdate>
/*     */ {
/*     */   private ColumnSelection cols;
/*     */ 
/*     */   protected OnDuplicateKeyUpdate(Q rootQuery, ColumnSelection cols)
/*     */   {
/*  16 */     super(rootQuery);
/*  17 */     this.cols = (cols == null ? new ColumnSelection() : cols.clone());
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate(Q rootQuery, Collection<String> e)
/*     */   {
/*  23 */     super(rootQuery, e);
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate(Q rootQuery, String[] e)
/*     */   {
/*  30 */     super(rootQuery, e);
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate(Q rootQuery, String e)
/*     */   {
/*  37 */     super(rootQuery, e);
/*     */   }
/*     */ 
/*     */   protected String getPrefix()
/*     */   {
/*  45 */     return " ON DUPLICATE KEY UPDATE ";
/*     */   }
/*     */ 
/*     */   protected StringBuilder buildInternal()
/*     */   {
/*  53 */     StringBuilder sb = super.buildInternal();
/*  54 */     sb.append(this.cols.buildDupKeyClause());
/*  55 */     return sb;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate removeCols(Collection<String> cols)
/*     */   {
/*  64 */     this.cols.removeCols(cols);
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate removeCols(String[] cols)
/*     */   {
/*  74 */     this.cols.removeCols(cols);
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate removeCols(Map<String, Object> cols)
/*     */   {
/*  84 */     this.cols.removeCols(cols);
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addCols(Collection<String> cols)
/*     */   {
/*  94 */     this.cols.addCols(cols);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addCols(String[] cols)
/*     */   {
/* 104 */     this.cols.addCols(cols);
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addCol(String colName, IValueModel model) {
/* 109 */     this.cols.addColumn(colName, model);
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addCol(String colName, String sqlLiteralString) {
/* 114 */     this.cols.addColumn(colName, sqlLiteralString);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addColDefault(String colName, Object defaultValue) {
/* 119 */     this.cols.addColumn(colName, new ValueModel(defaultValue));
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate clearCols()
/*     */   {
/* 130 */     this.cols.clearCols();
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public OnDuplicateKeyUpdate addCols(Map<String, Object> cols)
/*     */   {
/* 140 */     this.cols.addColsFromMap(cols);
/* 141 */     return this;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.OnDuplicateKeyUpdate
 * JD-Core Version:    0.6.2
 */