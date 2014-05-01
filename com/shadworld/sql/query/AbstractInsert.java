/*     */ package com.shadworld.sql.query;
/*     */ 
/*     */ import com.shadworld.sql.query.column.ColumnSelection;
/*     */ import com.shadworld.sql.query.column.ColumnSelectionItem;
/*     */ import com.shadworld.sql.query.column.IValueModel;
/*     */ import com.shadworld.sql.query.column.ValueModel;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ 
/*     */ public abstract class AbstractInsert<T extends Q> extends Q<T>
/*     */ {
/*     */   protected Collection data;
/*     */   protected Class dataClass;
/*     */   protected ColumnSelection colSel;
/*     */   protected String tableName;
/*     */ 
/*     */   public AbstractInsert(DataTable data, ColumnSelection colSel, String tableName)
/*     */   {
/*  27 */     super(null);
/*  28 */     this.data = data;
/*  29 */     this.dataClass = Record.class;
/*  30 */     this.colSel = colSel;
/*  31 */     this.tableName = tableName;
/*  32 */     if (colSel == null)
/*  33 */       values(data);
/*  34 */     this.rootQuery = this;
/*     */   }
/*     */ 
/*     */   public AbstractInsert(Record rec, ColumnSelection colSel, String tableName) {
/*  38 */     super(null);
/*  39 */     this.data = new DataTable();
/*  40 */     this.data.add(rec);
/*  41 */     this.dataClass = Record.class;
/*  42 */     this.colSel = colSel;
/*  43 */     this.tableName = tableName;
/*  44 */     if (colSel == null)
/*  45 */       values((DataTable)this.data);
/*  46 */     this.rootQuery = this;
/*     */   }
/*     */ 
/*     */   public AbstractInsert(Collection<Object> beans, ColumnSelection colSel, String tableName) {
/*  50 */     super(null);
/*  51 */     this.data = beans;
/*  52 */     this.dataClass = firstNonNull(beans).getClass();
/*  53 */     this.colSel = colSel;
/*  54 */     this.tableName = tableName;
/*  55 */     if (colSel == null)
/*  56 */       values(this.data);
/*  57 */     this.rootQuery = this;
/*     */   }
/*     */ 
/*     */   public AbstractInsert(Object bean, ColumnSelection colNames, String tableName) {
/*  61 */     super(null);
/*  62 */     this.data = Collections.singletonList(bean);
/*     */     try {
/*  64 */       this.dataClass = bean.getClass();
/*     */     } catch (NullPointerException e) {
/*  66 */       throw new IllegalArgumentException("cannot pass null bean to this constructor");
/*     */     }
/*  68 */     this.colSel = colNames;
/*  69 */     this.tableName = tableName;
/*  70 */     if (colNames == null)
/*  71 */       values(this.data);
/*  72 */     this.rootQuery = this;
/*     */   }
/*     */ 
/*     */   public T values(DataTable data) {
/*  76 */     this.data = data;
/*  77 */     if (this.colSel == null) {
/*  78 */       this.colSel = new ColumnSelection(data, true);
/*     */     }
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */   public T values(Collection<Object> beans)
/*     */   {
/*  94 */     this.data = beans;
/*  95 */     if (this.colSel == null) {
/*  96 */       this.colSel = new ColumnSelection(this.data);
/*     */     }
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   public T values(Object bean)
/*     */   {
/* 109 */     return values(Collections.singletonList(bean));
/*     */   }
/*     */ 
/*     */   public T removeCols(Collection<String> cols)
/*     */   {
/* 118 */     this.colSel.removeCols(cols);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public T removeCols(String[] cols)
/*     */   {
/* 128 */     this.colSel.removeCols(cols);
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */   public T removeCols(Map<String, Object> cols)
/*     */   {
/* 138 */     this.colSel.removeCols(cols);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public T addCols(Collection<String> cols)
/*     */   {
/* 148 */     this.colSel.addCols(cols);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public T addCols(String[] cols)
/*     */   {
/* 158 */     this.colSel.addCols(cols);
/* 159 */     return this;
/*     */   }
/*     */ 
/*     */   public T addCols(Map<String, Object> cols)
/*     */   {
/* 168 */     this.colSel.addColsFromMap(cols);
/* 169 */     return this;
/*     */   }
/*     */ 
/*     */   public T addCol(String colName, IValueModel model) {
/* 173 */     this.colSel.addColumn(colName, model);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public T addCol(String colName, String sqlLiteralString) {
/* 178 */     this.colSel.addColumn(colName, sqlLiteralString);
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */   public T addColDefault(String colName, Object defaultValue) {
/* 183 */     this.colSel.addColumn(colName, new ValueModel(defaultValue));
/* 184 */     return this;
/*     */   }
/*     */ 
/*     */   protected StringBuilder buildChildren(StringBuilder sb)
/*     */   {
/* 196 */     sb.append("\n");
/* 197 */     if (this.clauses.get(Select.class) != null) {
/* 198 */       sb.append("\n").append(((Q)this.clauses.get(Select.class)).buildInternal());
/*     */     } else {
/* 200 */       sb.append("VALUES ");
/* 201 */       boolean first = true;
/* 202 */       for (Iterator i = this.data.iterator(); i.hasNext(); ) {
/* 203 */         Object target = i.next();
/* 204 */         Iterator it = this.colSel.iterator();
/* 205 */         sb.append(first ? "(" : ",(");
/* 206 */         first = false;
/* 207 */         while (it.hasNext()) {
/* 208 */           ColumnSelectionItem colSelItem = (ColumnSelectionItem)it.next();
/* 209 */           Object value = colSelItem.getValue(target);
/* 210 */           if (colSelItem.isLastValueLiteral())
/* 211 */             sb.append(String.valueOf(value));
/*     */           else {
/* 213 */             sb.append(StringTools.wrap(StringEscapeUtils.escapeSql(value.toString()), "'"));
/*     */           }
/* 215 */           if (it.hasNext())
/* 216 */             sb.append(DELIMITER);
/*     */         }
/* 218 */         sb.append(")\n");
/*     */       }
/*     */     }
/* 221 */     if (this.clauses.get(OnDuplicateKeyUpdate.class) != null) {
/* 222 */       sb.append(((Q)this.clauses.get(OnDuplicateKeyUpdate.class)).buildInternal());
/*     */     }
/* 224 */     return sb;
/*     */   }
/*     */ 
/*     */   protected StringBuilder buildInternal() {
/* 228 */     StringBuilder sb = new StringBuilder(getPrefix());
/* 229 */     sb.append(this.tableName);
/* 230 */     if (this.colSel.size() > 0) {
/* 231 */       sb.append(" (");
/* 232 */       for (Iterator i = this.colSel.iterator(); i.hasNext(); ) {
/* 233 */         sb.append(StringTools.wrap(((ColumnSelectionItem)i.next()).getColumnName(), "`"));
/* 234 */         if (i.hasNext())
/* 235 */           sb.append(DELIMITER);
/*     */       }
/* 237 */       sb.append(")");
/*     */     }
/* 239 */     buildChildren(sb);
/* 240 */     return sb;
/*     */   }
/*     */ 
/*     */   private Object firstNonNull(Collection c) {
/* 244 */     for (Iterator localIterator = c.iterator(); localIterator.hasNext(); ) { Object o = localIterator.next();
/* 245 */       if (o != null)
/* 246 */         return o;
/*     */     }
/* 248 */     throw new IllegalArgumentException("Collection has no non-null values");
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.AbstractInsert
 * JD-Core Version:    0.6.2
 */