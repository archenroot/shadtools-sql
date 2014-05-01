/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import com.shadworld.sql.query.column.ColumnSelection;
/*    */ import com.shadworld.struct.DataTable;
/*    */ import com.shadworld.struct.Record;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class Replace extends AbstractInsert<Replace>
/*    */ {
/*    */   public Replace(Collection<Object> beans, ColumnSelection colNames, String tableName)
/*    */   {
/* 14 */     super(beans, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Replace(DataTable data, ColumnSelection colNames, String tableName)
/*    */   {
/* 19 */     super(data, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Replace(Object bean, ColumnSelection colNames, String tableName)
/*    */   {
/* 24 */     super(bean, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Replace(Record rec, ColumnSelection colNames, String tableName)
/*    */   {
/* 29 */     super(rec, colNames, tableName);
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 35 */     return "REPLACE INTO ";
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Replace
 * JD-Core Version:    0.6.2
 */