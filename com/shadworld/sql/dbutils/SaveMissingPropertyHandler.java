/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import com.shadworld.struct.Record;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class SaveMissingPropertyHandler extends MissingPropertyHandler
/*    */ {
/* 11 */   Record rec = new Record();
/*    */   boolean includeTableInKey;
/*    */ 
/*    */   public SaveMissingPropertyHandler(boolean includeTableInKey)
/*    */   {
/* 16 */     this.includeTableInKey = includeTableInKey;
/*    */   }
/*    */ 
/*    */   protected <T> Object handleMissingProperty(int colNumber, ResultSet rs, T bean, String colName, String colLabel, Object value)
/*    */   {
/* 22 */     String key = "";
/* 23 */     if (this.includeTableInKey) {
/*    */       try {
/* 25 */         ResultSetMetaData rsmd = rs.getMetaData();
/* 26 */         key = rsmd.getTableName(colNumber) + ".";
/*    */       }
/*    */       catch (SQLException e) {
/* 29 */         e.printStackTrace();
/*    */       }
/*    */     }
/* 32 */     this.rec.put(key + colLabel, value);
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   public Record getRecord() {
/* 37 */     return this.rec;
/*    */   }
/*    */ 
/*    */   public static SaveMissingPropertyHandler get(boolean includeTableInKey) {
/* 41 */     return new SaveMissingPropertyHandler(includeTableInKey);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.SaveMissingPropertyHandler
 * JD-Core Version:    0.6.2
 */