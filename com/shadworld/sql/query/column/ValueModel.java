/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ public class ValueModel<C> extends AbstractValueModel<C>
/*    */ {
/*    */   public ValueModel(C modelObject)
/*    */   {
/* 17 */     this.lastValue = modelObject;
/*    */   }
/*    */ 
/*    */   public C getValue(int row, int col, Object target)
/*    */   {
/* 22 */     return this.lastValue;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.ValueModel
 * JD-Core Version:    0.6.2
 */