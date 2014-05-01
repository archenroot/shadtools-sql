/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CompoundValueModel extends AbstractValueModel
/*    */ {
/* 14 */   List<IValueModel> models = new ArrayList();
/*    */ 
/*    */   public CompoundValueModel(IValueModel[] model)
/*    */   {
/* 18 */     this.models.addAll(Arrays.asList(model));
/*    */   }
/*    */ 
/*    */   public CompoundValueModel addModel(IValueModel model) {
/* 22 */     this.models.add(model);
/* 23 */     return this;
/*    */   }
/*    */ 
/*    */   public Object getValue(int row, int col, Object target)
/*    */   {
/* 28 */     for (IValueModel model : this.models) {
/* 29 */       this.lastValue = model.getValue(row, col, this.lastValue == null ? target : this.lastValue);
/*    */     }
/* 31 */     return this.lastValue;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.CompoundValueModel
 * JD-Core Version:    0.6.2
 */