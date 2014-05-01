/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class PlainResultSetHandler extends ShadHandler<ResultSet>
/*    */ {
/*    */   public PlainResultSetHandler()
/*    */   {
/* 14 */     super(false);
/*    */   }
/*    */ 
/*    */   public ResultSet handle(ResultSet rs)
/*    */     throws SQLException
/*    */   {
/* 23 */     return rs;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.PlainResultSetHandler
 * JD-Core Version:    0.6.2
 */