/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class Where extends Q<Where>
/*    */ {
/*  6 */   protected static String DELIMITER = " AND ";
/*    */ 
/*    */   public Where(Q rootQuery, Collection<String> e)
/*    */   {
/* 10 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Where(Q rootQuery, String[] e)
/*    */   {
/* 15 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Where(Q rootQuery, String e)
/*    */   {
/* 20 */     super(rootQuery, e);
/*    */   }
/*    */ 
/*    */   public Where(Q rootQuery)
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
/* 36 */     return "WHERE ";
/*    */   }
/*    */ 
/*    */   public GroupBy groupBy()
/*    */   {
/* 42 */     return super.groupBy();
/*    */   }
/*    */ 
/*    */   public GroupBy groupBy(String e)
/*    */   {
/* 48 */     return super.groupBy(e);
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy()
/*    */   {
/* 54 */     return super.orderBy();
/*    */   }
/*    */ 
/*    */   public OrderBy orderBy(String e)
/*    */   {
/* 60 */     return super.orderBy(e);
/*    */   }
/*    */ 
/*    */   public Limit limit(int offSet, int rowCount)
/*    */   {
/* 66 */     return super.limit(offSet, rowCount);
/*    */   }
/*    */ 
/*    */   public Limit limit(int rowCount)
/*    */   {
/* 72 */     return super.limit(rowCount);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Where
 * JD-Core Version:    0.6.2
 */