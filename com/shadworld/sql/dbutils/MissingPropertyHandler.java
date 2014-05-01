/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public abstract class MissingPropertyHandler
/*    */ {
/* 24 */   public static MissingPropertyHandler PrintMissingProperty = new MissingPropertyHandler()
/*    */   {
/*    */     protected <T> Object handleMissingProperty(int colNumber, ResultSet rs, T bean, String colName, String colLabel, Object value)
/*    */     {
/*    */       try
/*    */       {
/* 30 */         ResultSetMetaData rsmd = rs.getMetaData();
/* 31 */         String tableName = rsmd.getTableName(colNumber);
/* 32 */         System.out.println("Missing: " + colName + " | " + tableName + "." + colLabel + " for Class: " + bean.getClass().getSimpleName() + " - Value: " + value);
/*    */       }
/*    */       catch (SQLException e) {
/* 35 */         e.printStackTrace();
/*    */       }
/* 37 */       return null;
/*    */     }
/* 24 */   };
/*    */ 
/*    */   protected abstract <T> Object handleMissingProperty(int paramInt, ResultSet paramResultSet, T paramT, String paramString1, String paramString2, Object paramObject);
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.MissingPropertyHandler
 * JD-Core Version:    0.6.2
 */