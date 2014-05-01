/*    */ package com.shadworld.sql;
/*    */ 
/*    */ public class PostgreSql extends Sql
/*    */ {
/*    */   private String dbSchema;
/*    */ 
/*    */   public PostgreSql(String dbHost, String dbPort, String dbName, String dbSchema, String dbUser, String dbPassword)
/*    */   {
/* 16 */     super(dbHost, dbPort, dbName, dbUser, dbPassword);
/* 17 */     this.dbSchema = dbSchema;
/*    */   }
/*    */ 
/*    */   public PostgreSql(String dbName, String dbSchema, String dbUser, String dbPassword) {
/* 21 */     this("localhost", "5432", dbName, dbSchema, dbUser, dbPassword);
/*    */   }
/*    */ 
/*    */   protected String getDriverClass()
/*    */   {
/* 27 */     return "org.postgresql.Driver";
/*    */   }
/*    */ 
/*    */   protected String buildUrl()
/*    */   {
/* 32 */     return "jdbc:postgresql:" + this.dbName + "//" + this.dbHost + ":" + this.dbPort + "/" + this.dbSchema + getJdbcOptionsUrlString();
/*    */   }
/*    */ 
/*    */   public String getDbSchema()
/*    */   {
/* 39 */     return this.dbSchema;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.PostgreSql
 * JD-Core Version:    0.6.2
 */