/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class GroupBy extends Q
/*    */ {
/*    */   public GroupBy(Q rootQuery, Collection e)
/*    */   {
/*  8 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public GroupBy(Q rootQuery, String[] e)
/*    */   {
/* 13 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public GroupBy(Q rootQuery, String e)
/*    */   {
/* 18 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public GroupBy(Q rootQuery)
/*    */   {
/* 23 */     super(rootQuery);
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 29 */     return "GROUP BY ";
/*    */   }
/*    */ 
/*    */   public Having having()
/*    */   {
/* 35 */     return super.having();
/*    */   }
/*    */ 
/*    */   public Having having(String e)
/*    */   {
/* 41 */     return super.having(e);
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy()
/*    */   {
/* 47 */     return super.orderBy();
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy(String e)
/*    */   {
/* 53 */     return super.orderBy(e);
/*    */   }
/*    */ 
/*    */   public Limit limit(int offSet, int rowCount)
/*    */   {
/* 59 */     return super.limit(offSet, rowCount);
/*    */   }
/*    */ 
/*    */   public Limit limit(int rowCount)
/*    */   {
/* 65 */     return super.limit(rowCount);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.GroupBy
 * JD-Core Version:    0.6.2
 */