/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import com.shadworld.sql.query.SqlLiteral;
/*    */ 
/*    */ public class SqlLiteralValueModel extends AbstractValueModel<SqlLiteral>
/*    */ {
/*    */   public SqlLiteralValueModel(SqlLiteral literal)
/*    */   {
/* 24 */     this.lastValue = literal;
/*    */   }
/*    */ 
/*    */   public SqlLiteralValueModel(String literal)
/*    */   {
/* 36 */     this.lastValue = new SqlLiteral(literal);
/*    */   }
/*    */ 
/*    */   public SqlLiteral getValue(int row, int col, Object target)
/*    */   {
/* 41 */     return (SqlLiteral)this.lastValue;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.SqlLiteralValueModel
 * JD-Core Version:    0.6.2
 */