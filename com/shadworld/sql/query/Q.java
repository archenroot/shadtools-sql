/*     */ package com.shadworld.sql.query;
/*     */ 
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class Q<T extends Q>
/*     */ {
/*  18 */   protected static String DELIMITER = ", ";
/*     */   protected Q rootQuery;
/*  21 */   protected HashMap<Class<? extends Q>, Q> clauses = new HashMap();
/*     */ 
/*  38 */   protected List<Q<T>.Element> elements = new ArrayList();
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected String getDelimiter()
/*     */   {
/*  35 */     return DELIMITER;
/*     */   }
/*     */ 
/*     */   protected Q(Q rootQuery)
/*     */   {
/*  43 */     registerRootQuery(rootQuery);
/*     */   }
/*     */ 
/*     */   protected Q(Q rootQuery, String e)
/*     */   {
/*  48 */     registerRootQuery(rootQuery);
/*  49 */     a(e);
/*     */   }
/*     */ 
/*     */   protected Q(Q rootQuery, String[] e)
/*     */   {
/*  54 */     registerRootQuery(rootQuery);
/*  55 */     a(e);
/*     */   }
/*     */ 
/*     */   protected Q(Q rootQuery, Collection<String> e)
/*     */   {
/*  60 */     registerRootQuery(rootQuery);
/*  61 */     a(e);
/*     */   }
/*     */ 
/*     */   public final String build()
/*     */   {
/*  70 */     StringBuilder sb = getRootQuery().buildInternal();
/*  71 */     return ";";
/*     */   }
/*     */ 
/*     */   protected StringBuilder buildInternal() {
/*  75 */     StringBuilder sb = new StringBuilder(getPrefix());
/*  76 */     for (Iterator i = this.elements.iterator(); i.hasNext(); ) {
/*  77 */       sb.append(((Element)i.next()).text);
/*  78 */       if (i.hasNext())
/*  79 */         sb.append(getDelimiter());
/*     */     }
/*  81 */     buildChildren(sb);
/*  82 */     return sb;
/*     */   }
/*     */ 
/*     */   protected StringBuilder buildChildren(StringBuilder sb)
/*     */   {
/*  93 */     return sb;
/*     */   }
/*     */ 
/*     */   public T a(String e)
/*     */   {
/* 105 */     this.elements.add(new Element(e));
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public T a(String[] e)
/*     */   {
/* 118 */     for (String element : e)
/* 119 */       this.elements.add(new Element(element));
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   public T a(Collection<String> e)
/*     */   {
/* 132 */     for (String element : e)
/* 133 */       this.elements.add(new Element(element));
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   protected abstract String getPrefix();
/*     */ 
/*     */   public static Select Select() {
/* 140 */     return new Select(null);
/*     */   }
/*     */ 
/*     */   public static Select SelectDistinct() {
/* 144 */     return new Select("DISTINCT", null);
/*     */   }
/*     */ 
/*     */   public static Insert Insert(String tableName, DataTable table) {
/* 148 */     return new Insert(table, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Insert Insert(String tableName, Record rec) {
/* 152 */     return new Insert(rec, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Insert Insert(String tableName, Object bean) {
/* 156 */     return new Insert(bean, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Insert Insert(String tableName, Collection beans) {
/* 160 */     return new Insert(beans, null, tableName);
/*     */   }
/*     */ 
/*     */   public static InsertIgnore InsertIgnore(String tableName, DataTable table) {
/* 164 */     return new InsertIgnore(table, null, tableName);
/*     */   }
/*     */ 
/*     */   public static InsertIgnore InsertIgnore(String tableName, Record rec) {
/* 168 */     return new InsertIgnore(rec, null, tableName);
/*     */   }
/*     */ 
/*     */   public static InsertIgnore InsertIgnore(String tableName, Object bean) {
/* 172 */     return new InsertIgnore(bean, null, tableName);
/*     */   }
/*     */ 
/*     */   public static InsertIgnore InsertIgnore(String tableName, Collection beans) {
/* 176 */     return new InsertIgnore(beans, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Replace Replace(String tableName, DataTable table) {
/* 180 */     return new Replace(table, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Replace Replace(String tableName, Record rec) {
/* 184 */     return new Replace(rec, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Replace Replace(String tableName, Object bean) {
/* 188 */     return new Replace(bean, null, tableName);
/*     */   }
/*     */ 
/*     */   public static Replace Replace(String tableName, Collection beans) {
/* 192 */     return new Replace(beans, null, tableName);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 203 */     return build();
/*     */   }
/*     */ 
/*     */   public Q getRootQuery()
/*     */   {
/* 211 */     return this.rootQuery;
/*     */   }
/*     */ 
/*     */   private void registerRootQuery(Q rootQuery) {
/* 215 */     this.rootQuery = rootQuery;
/* 216 */     if (rootQuery != null) {
/* 217 */       Q q = (Q)rootQuery.clauses.put(getClass(), this);
/* 218 */       if (q != null)
/* 219 */         throw new IllegalArgumentException("Attempted to add multiple clauses of type: " + getClass().getSimpleName());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Where where(String e)
/*     */   {
/* 260 */     Where where = (Where)getRootQuery().clauses.get(Where.class);
/* 261 */     if (where == null) {
/* 262 */       where = new Where(getRootQuery());
/*     */     }
/* 264 */     where.a(e);
/* 265 */     return where;
/*     */   }
/*     */ 
/*     */   protected Where where() {
/* 269 */     Where where = (Where)getRootQuery().clauses.get(Where.class);
/* 270 */     if (where == null) {
/* 271 */       where = new Where(getRootQuery());
/*     */     }
/* 273 */     return where;
/*     */   }
/*     */ 
/*     */   protected GroupBy groupBy(String e) {
/* 277 */     GroupBy groupBy = (GroupBy)getRootQuery().clauses.get(GroupBy.class);
/* 278 */     if (groupBy == null) {
/* 279 */       groupBy = new GroupBy(getRootQuery());
/*     */     }
/* 281 */     groupBy.a(e);
/* 282 */     return groupBy;
/*     */   }
/*     */ 
/*     */   protected GroupBy groupBy() {
/* 286 */     GroupBy groupBy = (GroupBy)getRootQuery().clauses.get(GroupBy.class);
/* 287 */     if (groupBy == null) {
/* 288 */       groupBy = new GroupBy(getRootQuery());
/*     */     }
/* 290 */     return groupBy;
/*     */   }
/*     */ 
/*     */   protected Having having(String e) {
/* 294 */     Having having = (Having)getRootQuery().clauses.get(Having.class);
/* 295 */     if (having == null) {
/* 296 */       having = new Having(getRootQuery());
/*     */     }
/* 298 */     having.a(e);
/* 299 */     return having;
/*     */   }
/*     */ 
/*     */   protected Having having() {
/* 303 */     Having having = (Having)getRootQuery().clauses.get(Having.class);
/* 304 */     if (having == null) {
/* 305 */       having = new Having(getRootQuery());
/*     */     }
/* 307 */     return having;
/*     */   }
/*     */ 
/*     */   protected Limit limit(int rowCount) {
/* 311 */     return limit(-1, rowCount);
/*     */   }
/*     */ 
/*     */   protected Limit limit(int offSet, int rowCount) {
/* 315 */     Limit limit = (Limit)getRootQuery().clauses.get(Limit.class);
/* 316 */     if (limit == null) {
/* 317 */       limit = new Limit(getRootQuery(), offSet, rowCount);
/*     */     }
/* 319 */     return limit;
/*     */   }
/*     */ 
/*     */   protected OrderBy orderBy(String e) {
/* 323 */     OrderBy orderBy = (OrderBy)getRootQuery().clauses.get(OrderBy.class);
/* 324 */     if (orderBy == null) {
/* 325 */       orderBy = new OrderBy(getRootQuery());
/*     */     }
/* 327 */     orderBy.a(e);
/* 328 */     return orderBy;
/*     */   }
/*     */ 
/*     */   protected OrderBy orderBy() {
/* 332 */     OrderBy orderBy = (OrderBy)getRootQuery().clauses.get(OrderBy.class);
/* 333 */     if (orderBy == null) {
/* 334 */       orderBy = new OrderBy(getRootQuery());
/*     */     }
/* 336 */     return orderBy;
/*     */   }
/*     */ 
/*     */   protected class Element
/*     */   {
/*     */     String text;
/*     */     Q q;
/*     */     String alias;
/*     */ 
/*     */     public Element(String text)
/*     */     {
/* 243 */       this.text = text;
/*     */     }
/*     */ 
/*     */     public Element(Q q)
/*     */     {
/* 248 */       this.q = q;
/*     */     }
/*     */ 
/*     */     public String getText() {
/* 252 */       if (this.q != null)
/* 253 */         return this.q.build();
/* 254 */       return this.text;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Q
 * JD-Core Version:    0.6.2
 */