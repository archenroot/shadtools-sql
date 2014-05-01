/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import com.shadworld.sql.orm.BeanRegistry;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BeanMappedIdValueModel extends AbstractValueModel<Object>
/*    */ {
/*    */   private PropertyDescriptor prop;
/*    */ 
/*    */   public BeanMappedIdValueModel(PropertyDescriptor prop)
/*    */   {
/* 22 */     this.prop = prop;
/*    */   }
/*    */ 
/*    */   public BeanMappedIdValueModel(Class beanClass, String propName)
/*    */   {
/* 27 */     this.prop = BeanRegistry.get().getPropertyDescriptor(beanClass, propName);
/*    */   }
/*    */ 
/*    */   public Object getValue(int row, int col, Object target)
/*    */   {
/*    */     try
/*    */     {
/* 38 */       Object targetBean = this.prop.getReadMethod().invoke(target, new Object[0]);
/* 39 */       List tProps = BeanRegistry.get().getIdPropertiesOrDefault(this.prop.getPropertyType());
/* 40 */       if ((tProps == null) || (tProps.size() == 0))
/* 41 */         return null;
/* 42 */       PropertyDescriptor tProp = (PropertyDescriptor)tProps.get(0);
/* 43 */       this.lastValue = tProp.getReadMethod().invoke(targetBean, new Object[0]);
/* 44 */       return this.lastValue; } catch (Exception e) {
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.BeanMappedIdValueModel
 * JD-Core Version:    0.6.2
 */