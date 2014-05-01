/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import org.apache.commons.dbutils.ResultSetHandler;
/*    */ 
/*    */ public abstract class ShadHandler<T>
/*    */   implements ResultSetHandler<T>
/*    */ {
/*  7 */   protected boolean typed = false;
/*    */ 
/*  9 */   protected ShadBeanProcessor processor = new ShadBeanProcessor();
/*    */ 
/*    */   public ShadHandler(boolean typed)
/*    */   {
/* 13 */     this.typed = typed;
/*    */   }
/*    */ 
/*    */   public boolean isTyped()
/*    */   {
/* 20 */     return this.typed;
/*    */   }
/*    */ 
/*    */   public ShadBeanProcessor getProcessor()
/*    */   {
/* 27 */     return this.processor;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.ShadHandler
 * JD-Core Version:    0.6.2
 */