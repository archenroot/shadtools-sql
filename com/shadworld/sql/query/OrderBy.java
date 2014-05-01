/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class OrderBy extends Q
/*    */ {
/*    */   public OrderBy(Q rootQuery, Collection e)
/*    */   {
/*  8 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public OrderBy(Q rootQuery, String[] e)
/*    */   {
/* 13 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public OrderBy(Q rootQuery, String e)
/*    */   {
/* 18 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public OrderBy(Q rootQuery)
/*    */   {
/* 23 */     super(rootQuery);
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 29 */     return "ORDER BY ";
/*    */   }
/*    */ 
/*    */   public Limit limit(int offSet, int rowCount)
/*    */   {
/* 35 */     return super.limit(offSet, rowCount);
/*    */   }
/*    */ 
/*    */   public Limit limit(int rowCount)
/*    */   {
/* 41 */     return super.limit(rowCount);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.OrderBy
 * JD-Core Version:    0.6.2
 */