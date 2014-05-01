/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import com.shadworld.sql.query.SqlLiteral;
/*    */ 
/*    */ public abstract class AbstractValueModel<C>
/*    */   implements IValueModel<C>
/*    */ {
/*    */   protected Object lastValue;
/*    */ 
/*    */   public abstract C getValue(int paramInt1, int paramInt2, Object paramObject);
/*    */ 
/*    */   public final C getValue(int row, Object target)
/*    */   {
/* 21 */     return getValue(row, -1, target);
/*    */   }
/*    */ 
/*    */   public final C getValue(Object target)
/*    */   {
/* 26 */     return getValue(-1, -1, target);
/*    */   }
/*    */ 
/*    */   public boolean isLastValueLiteral()
/*    */   {
/* 34 */     if (this.lastValue == null)
/* 35 */       return true;
/* 36 */     if ((this.lastValue instanceof SqlLiteral))
/* 37 */       return true;
/* 38 */     if ((this.lastValue instanceof Boolean)) {
/* 39 */       return true;
/*    */     }
/*    */ 
/* 46 */     return false;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.AbstractValueModel
 * JD-Core Version:    0.6.2
 */