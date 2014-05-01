/*    */ package com.shadworld.sql;
/*    */ 
/*    */ public class MySqlPool extends MySql
/*    */ {
/*    */   public MySqlPool(String dbName, String dbUser, String dbPassword)
/*    */   {
/*  6 */     super(dbName, dbUser, dbPassword);
/*    */   }
/*    */ 
/*    */   public MySqlPool(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword)
/*    */   {
/* 11 */     super(dbHost, dbPort, dbName, dbUser, dbPassword);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.MySqlPool
 * JD-Core Version:    0.6.2
 */