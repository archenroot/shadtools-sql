/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class From extends Q<From>
/*    */ {
/*    */   public From(Q rootQuery, Collection<String> e)
/*    */   {
/* 10 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public From(Q rootQuery, String[] e)
/*    */   {
/* 15 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public From(Q rootQuery, String e)
/*    */   {
/* 20 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public From(Q rootQuery)
/*    */   {
/* 25 */     super(rootQuery);
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 31 */     return "FROM ";
/*    */   }
/*    */ 
/*    */   public Where where()
/*    */   {
/* 37 */     return super.where();
/*    */   }
/*    */ 
/*    */   public Where where(String e)
/*    */   {
/* 43 */     return super.where(e);
/*    */   }
/*    */ 
/*    */   public GroupBy groupBy()
/*    */   {
/* 49 */     return super.groupBy();
/*    */   }
/*    */ 
/*    */   public GroupBy groupBy(String e)
/*    */   {
/* 55 */     return super.groupBy(e);
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy()
/*    */   {
/* 61 */     return super.orderBy();
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy(String e)
/*    */   {
/* 67 */     return super.orderBy(e);
/*    */   }
/*    */ 
/*    */   public Limit limit(int offSet, int rowCount)
/*    */   {
/* 73 */     return super.limit(offSet, rowCount);
/*    */   }
/*    */ 
/*    */   public Limit limit(int rowCount)
/*    */   {
/* 79 */     return super.limit(rowCount);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.From
 * JD-Core Version:    0.6.2
 */