/*     */ package com.shadworld.sql.query.column;
/*     */ 
/*     */ import com.shadworld.sql.orm.BeanRegistry;
/*     */ import com.shadworld.sql.orm.BeanRegistry.RegistryEntry;
/*     */ import com.shadworld.sql.orm.JPAUtil;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import com.shadworld.utils.StructUtils;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.persistence.Transient;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ 
/*     */ public class ColumnSelection
/*     */   implements Iterable<ColumnSelectionItem>
/*     */ {
/*  26 */   private HashMap<String, ColumnSelectionItem> cols = new HashMap();
/*     */   private Class targetClass;
/*     */ 
/*     */   public ColumnSelection()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ColumnSelection(Class beanClass)
/*     */   {
/*  35 */     this.targetClass = beanClass;
/*  36 */     populateFromBeanClass(beanClass);
/*     */   }
/*     */ 
/*     */   public ColumnSelection(Collection<Object> beans)
/*     */   {
/*  41 */     Iterator i = beans.iterator();
/*  42 */     Class beanClass = null;
/*  43 */     while ((i.hasNext()) && (beanClass == null)) {
/*  44 */       Object bean = i.next();
/*  45 */       if (bean != null) {
/*  46 */         beanClass = bean.getClass();
/*     */       }
/*     */     }
/*  49 */     this.targetClass = beanClass;
/*  50 */     populateFromBeanClass(beanClass);
/*     */   }
/*     */ 
/*     */   private ColumnSelection(HashMap<String, ColumnSelectionItem> cols)
/*     */   {
/*  59 */     this.cols = cols;
/*     */   }
/*     */ 
/*     */   public ColumnSelection(DataTable table, boolean normalize)
/*     */   {
/*  69 */     this.targetClass = Map.class;
/*  70 */     Record rec = StructUtils.getKeySuperSet(table);
/*  71 */     for (Iterator localIterator = rec.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/*  72 */       String ky = String.valueOf(key);
/*  73 */       this.cols.put(ky, new ColumnSelectionItem(ky, ky, rec));
/*     */     }
/*  75 */     if (normalize)
/*  76 */       table = StructUtils.padTable(table, this.cols);
/*     */   }
/*     */ 
/*     */   public ColumnSelection(List<String> cols)
/*     */   {
/*  82 */     for (String col : cols)
/*  83 */       this.cols.put(col, new ColumnSelectionItem(col));
/*     */   }
/*     */ 
/*     */   private Record getClassRecFromBeanClass(Class beanClass)
/*     */   {
/*  92 */     Record classRec = new Record();
/*  93 */     PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(beanClass);
/*  94 */     for (PropertyDescriptor prop : props) {
/*  95 */       if ((prop.getReadMethod() != null) && 
/*  96 */         (!prop.getName().equalsIgnoreCase("class")) && 
/*  97 */         (!Collection.class.isAssignableFrom(prop.getPropertyType())) && 
/*  98 */         (JPAUtil.findAnnotationForProperty(Transient.class, beanClass, prop) == null)) {
/*  99 */         String colName = BeanRegistry.get().getPropertyColumnName(beanClass, prop);
/* 100 */         classRec.put(colName, prop.getPropertyType());
/*     */       }
/*     */     }
/* 103 */     return classRec;
/*     */   }
/*     */ 
/*     */   private void populateFromBeanClass(Class beanClass)
/*     */   {
/* 108 */     this.cols.clear();
/* 109 */     PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(beanClass);
/* 110 */     for (PropertyDescriptor prop : props)
/* 111 */       if ((prop.getReadMethod() != null) && 
/* 112 */         (!prop.getName().equalsIgnoreCase("class")) && 
/* 113 */         (!Collection.class.isAssignableFrom(prop.getPropertyType())) && 
/* 114 */         (JPAUtil.findAnnotationForProperty(Transient.class, beanClass, prop) == null)) {
/* 115 */         String colName = BeanRegistry.get().getPropertyColumnName(beanClass, prop);
/* 116 */         addProperty(prop);
/*     */       }
/*     */   }
/*     */ 
/*     */   public ColumnSelection removeCols(Collection<String> cols)
/*     */   {
/* 127 */     if ((this.targetClass == null) || (Map.class.isAssignableFrom(this.targetClass))) {
/* 128 */       for (String col : cols)
/* 129 */         this.cols.remove(col);
/*     */     }
/*     */     else {
/* 132 */       for (String col : cols) {
/* 133 */         if (this.cols.remove(col) == null) {
/* 134 */           String tryName = BeanRegistry.get().getPropertyColumnName(this.targetClass, col);
/* 135 */           if ((tryName == null) || (this.cols.remove(tryName) == null)) {
/* 136 */             tryName = BeanRegistry.get().getColumnPropertyName(this.targetClass, col);
/* 137 */             this.cols.remove(tryName);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 142 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection removeCols(String[] cols) {
/* 146 */     removeCols(Arrays.asList(cols));
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection removeCols(Map<String, Object> cols) {
/* 151 */     for (String col : cols.keySet()) {
/* 152 */       this.cols.remove(col);
/*     */     }
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumn(String colName) {
/* 158 */     if (this.targetClass == null) {
/* 159 */       this.cols.put(colName, new ColumnSelectionItem(colName));
/* 160 */     } else if (Map.class.isAssignableFrom(this.targetClass)) {
/* 161 */       this.cols.put(colName, new ColumnSelectionItem(colName, new MapValueModel(colName)));
/*     */     } else {
/* 163 */       PropertyDescriptor prop = BeanRegistry.get().getPropertyDescriptor(this.targetClass, BeanRegistry.get().getColumnPropertyName(this.targetClass, colName));
/* 164 */       addProperty(prop);
/*     */     }
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumn(String colName, String sqlLiteralString) {
/* 170 */     this.cols.put(colName, new ColumnSelectionItem(colName, sqlLiteralString));
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumnValue(String colName, Object value) {
/* 175 */     this.cols.put(colName, new ColumnSelectionItem(colName, new ValueModel(value)));
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumn(String colName, IValueModel model)
/*     */   {
/* 187 */     this.cols.put(colName, new ColumnSelectionItem(colName, model));
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumn(String colName, String colKey, Map map) {
/* 192 */     this.cols.put(colName, new ColumnSelectionItem(colName, colKey, map));
/* 193 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColumn(String colName, Map map) {
/* 197 */     this.cols.put(colName, new ColumnSelectionItem(colName, colName, map));
/* 198 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addProperty(String propName) {
/* 202 */     PropertyDescriptor prop = BeanRegistry.get().getPropertyDescriptor(this.targetClass, propName);
/* 203 */     addProperty(prop);
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addPropertyValue(String propName, Object value) {
/* 208 */     String colName = BeanRegistry.get().getColumnPropertyName(this.targetClass, propName);
/* 209 */     this.cols.put(colName, new ColumnSelectionItem(colName, new ValueModel(value)));
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addProperty(String propName, String sqlLiteralString) {
/* 214 */     String colName = BeanRegistry.get().getColumnPropertyName(this.targetClass, propName);
/* 215 */     this.cols.put(colName, new ColumnSelectionItem(colName, new SqlLiteralValueModel(sqlLiteralString)));
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addProperty(String propName, IValueModel model) {
/* 220 */     String colName = BeanRegistry.get().getPropertyColumnName(this.targetClass, propName);
/* 221 */     addColumn(colName, model);
/* 222 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addProperty(PropertyDescriptor prop) {
/* 226 */     String colName = BeanRegistry.get().getPropertyColumnName(this.targetClass, prop);
/* 227 */     if (BeanRegistry.get().getRegistryEntry(this.targetClass).isMappedProperty(prop))
/* 228 */       this.cols.put(colName, new ColumnSelectionItem(colName, new BeanMappedIdValueModel(prop)));
/*     */     else {
/* 230 */       this.cols.put(colName, new ColumnSelectionItem(colName, new BeanValueModel(prop)));
/*     */     }
/* 232 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addCols(Collection<String> cols)
/*     */   {
/* 238 */     if (this.targetClass == null) {
/* 239 */       for (String col : cols)
/* 240 */         this.cols.put(col, null);
/*     */     }
/* 242 */     else if (Map.class.isAssignableFrom(this.targetClass)) {
/* 243 */       for (String col : cols) {
/* 244 */         this.cols.put(col, new ColumnSelectionItem(col, new MapValueModel(col)));
/*     */       }
/*     */     }
/*     */     else {
/* 248 */       for (String col : cols) {
/* 249 */         if (this.cols.remove(col) == null) {
/* 250 */           String tryName = BeanRegistry.get().getPropertyColumnName(this.targetClass, col);
/* 251 */           if ((tryName == null) || (this.cols.remove(tryName) == null)) {
/* 252 */             tryName = BeanRegistry.get().getColumnPropertyName(this.targetClass, col);
/* 253 */             this.cols.remove(tryName);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 258 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addCols(String[] cols) {
/* 262 */     addCols(Arrays.asList(cols));
/* 263 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection clearCols() {
/* 267 */     this.cols.clear();
/* 268 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addCols(Map<String, ColumnSelectionItem> cols) {
/* 272 */     for (String col : cols.keySet()) {
/* 273 */       this.cols.put(col, (ColumnSelectionItem)cols.get(col));
/*     */     }
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   public ColumnSelection addColsFromMap(Map<String, Object> cols) {
/* 279 */     for (String col : cols.keySet()) {
/* 280 */       addColumn(col, cols);
/*     */     }
/* 282 */     return this;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String buildInsertString(String encloser)
/*     */   {
/* 292 */     StringBuilder sb = new StringBuilder("(");
/* 293 */     sb.append(StringTools.concatStrings(this.cols.keySet(), ",", "`"));
/* 294 */     sb.append(")");
/* 295 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String buildInsertString()
/*     */   {
/* 304 */     return buildInsertString("`");
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public String buildDupKeyClause()
/*     */   {
/* 314 */     StringBuilder sqlString = new StringBuilder();
/* 315 */     for (Iterator keys = this.cols.keySet().iterator(); keys.hasNext(); ) {
/* 316 */       String tempString = StringTools.wrap(keys.next().toString().toLowerCase(), "`");
/* 317 */       sqlString.append(tempString).append(" = VALUES (").append(tempString).append(")");
/* 318 */       if (keys.hasNext())
/* 319 */         sqlString.append(", ");
/*     */     }
/* 321 */     return sqlString.toString();
/*     */   }
/*     */ 
/*     */   public ColumnSelection clone() {
/* 325 */     return new ColumnSelection((HashMap)this.cols.clone());
/*     */   }
/*     */ 
/*     */   public Iterator<ColumnSelectionItem> iterator() {
/* 329 */     return this.cols.values().iterator();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 337 */     return this.cols.size();
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.ColumnSelection
 * JD-Core Version:    0.6.2
 */