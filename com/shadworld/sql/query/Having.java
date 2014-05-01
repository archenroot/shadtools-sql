/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class Having extends Q<Having>
/*    */ {
/*  7 */   protected static String DELIMITER = " AND ";
/*    */ 
/*    */   public Having(Q rootQuery, Collection<String> e) {
/* 10 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Having(Q rootQuery, String[] e)
/*    */   {
/* 15 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Having(Q rootQuery, String e)
/*    */   {
/* 20 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Having(Q rootQuery)
/*    */   {
/* 25 */     super(rootQuery);
/*    */   }
/*    */ 
/*    */   protected String getDelimiter()
/*    */   {
/* 31 */     return DELIMITER;
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 36 */     return "HAVING ";
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy()
/*    */   {
/* 42 */     return super.orderBy();
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy(String e)
/*    */   {
/* 48 */     return super.orderBy(e);
/*    */   }
/*    */ 
/*    */   public Limit limit(int offSet, int rowCount)
/*    */   {
/* 54 */     return super.limit(offSet, rowCount);
/*    */   }
/*    */ 
/*    */   public Limit limit(int rowCount)
/*    */   {
/* 60 */     return super.limit(rowCount);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Having
 * JD-Core Version:    0.6.2
 */