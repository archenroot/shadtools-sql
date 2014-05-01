/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ public class Limit extends Q
/*    */ {
/*    */   private int offSet;
/*  7 */   private int rowCount = -1;
/*    */ 
/*    */   public Limit(Q rootQuery, int rowCount) {
/* 10 */     super(rootQuery);
/* 11 */     this.rowCount = rowCount;
/*    */   }
/*    */ 
/*    */   public Limit(Q rootQuery, int offSet, int rowCount) {
/* 15 */     super(rootQuery);
/* 16 */     this.rowCount = rowCount;
/* 17 */     this.offSet = offSet;
/*    */   }
/*    */ 
/*    */   public StringBuilder buildInternal()
/*    */   {
/* 24 */     StringBuilder sb = new StringBuilder(getPrefix());
/* 25 */     if (this.offSet >= 0)
/* 26 */       sb.append(this.offSet).append(", ").append(this.rowCount);
/*    */     else {
/* 28 */       sb.append(this.rowCount);
/*    */     }
/* 30 */     return sb;
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 36 */     return "LIMIT ";
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Limit
 * JD-Core Version:    0.6.2
 */