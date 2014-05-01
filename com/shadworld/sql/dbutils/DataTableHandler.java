/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import com.shadworld.struct.DataTable;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class DataTableHandler extends ShadHandler<DataTable>
/*    */ {
/*    */   public DataTableHandler(boolean typed)
/*    */   {
/* 11 */     super(typed);
/*    */   }
/*    */ 
/*    */   public DataTable handle(ResultSet rs)
/*    */     throws SQLException
/*    */   {
/* 17 */     return this.typed ? this.processor.toTypedDataTable(rs) : this.processor.toStringDataTable(rs);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.DataTableHandler
 * JD-Core Version:    0.6.2
 */