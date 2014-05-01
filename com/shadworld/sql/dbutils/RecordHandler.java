/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import com.shadworld.struct.Record;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class RecordHandler extends ShadHandler<Record>
/*    */ {
/* 21 */   private ShadBeanProcessor processor = new ShadBeanProcessor();
/*    */ 
/*    */   public RecordHandler(boolean typed)
/*    */   {
/* 17 */     super(typed);
/*    */   }
/*    */ 
/*    */   public Record handle(ResultSet rs)
/*    */     throws SQLException
/*    */   {
/* 25 */     return this.typed ? this.processor.toTypedRecord(rs) : this.processor.toStringRecord(rs);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.RecordHandler
 * JD-Core Version:    0.6.2
 */