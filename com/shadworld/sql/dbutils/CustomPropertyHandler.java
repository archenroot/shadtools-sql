/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ 
/*    */ public abstract class CustomPropertyHandler
/*    */ {
/* 13 */   boolean callSetter = true;
/*    */ 
/*    */   protected abstract <T> Object handleCustomProperty(int paramInt, ResultSet paramResultSet, ResultSetMetaData paramResultSetMetaData, T paramT, Object paramObject);
/*    */ 
/*    */   protected void dontCallSetter()
/*    */   {
/* 30 */     this.callSetter = false;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.CustomPropertyHandler
 * JD-Core Version:    0.6.2
 */