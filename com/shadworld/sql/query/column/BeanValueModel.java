/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import com.shadworld.sql.orm.BeanRegistry;
/*    */ import com.shadworld.util.Time;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class BeanValueModel extends AbstractValueModel<String>
/*    */ {
/*    */   private PropertyDescriptor prop;
/*    */ 
/*    */   public BeanValueModel(PropertyDescriptor prop)
/*    */   {
/* 23 */     this.prop = prop;
/*    */   }
/*    */ 
/*    */   public BeanValueModel(Class beanClass, String propName)
/*    */   {
/* 28 */     this.prop = BeanRegistry.get().getPropertyDescriptor(beanClass, propName);
/*    */   }
/*    */ 
/*    */   public synchronized String getValue(int row, int col, Object target)
/*    */   {
/*    */     try
/*    */     {
/* 35 */       this.lastValue = this.prop.getReadMethod().invoke(target, new Object[0]);
/* 36 */       if ((this.lastValue != null) && ((this.lastValue instanceof Date))) {
/* 37 */         return Time.sqlDateTimeFormat.format((Date)this.lastValue);
/*    */       }
/* 39 */       return String.valueOf(this.lastValue); } catch (Exception e) {
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.BeanValueModel
 * JD-Core Version:    0.6.2
 */