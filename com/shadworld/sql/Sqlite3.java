/*    */ package com.shadworld.sql;
/*    */ 
/*    */ public class Sqlite3 extends Sql
/*    */ {
/*    */   private String filename;
/*    */ 
/*    */   public Sqlite3(String filename)
/*    */   {
/* 10 */     super(filename, null, null);
/* 11 */     this.filename = filename;
/*    */   }
/*    */ 
/*    */   protected String getDriverClass()
/*    */   {
/* 22 */     return "org.sqlite.JDBC";
/*    */   }
/*    */ 
/*    */   protected String buildUrl()
/*    */   {
/* 28 */     return "jdbc:sqlite:" + this.filename;
/*    */   }
/*    */ 
/*    */   protected int getResultSetType() {
/* 32 */     return 1003;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.Sqlite3
 * JD-Core Version:    0.6.2
 */