/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MapValueModel<C> extends AbstractValueModel<C>
/*    */ {
/*    */   private Object key;
/*    */ 
/*    */   public MapValueModel(Object key)
/*    */   {
/* 22 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public C getValue(int row, int col, Object target)
/*    */   {
/* 27 */     this.lastValue = ((Map)target).get(this.key);
/* 28 */     return this.lastValue;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.MapValueModel
 * JD-Core Version:    0.6.2
 */