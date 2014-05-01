/*    */ package com.shadworld.sql.query.column;
/*    */ 
/*    */ import com.shadworld.sql.orm.BeanRegistry;
/*    */ import com.shadworld.utils.StringTools;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class EmbeddedStringCollectionValueModel extends AbstractValueModel<String>
/*    */ {
/*    */   private PropertyDescriptor prop;
/*    */ 
/*    */   public EmbeddedStringCollectionValueModel(PropertyDescriptor prop)
/*    */   {
/* 16 */     this.prop = prop;
/*    */   }
/*    */ 
/*    */   public EmbeddedStringCollectionValueModel(Class beanClass, String propName) {
/* 20 */     this(BeanRegistry.get().getPropertyDescriptor(beanClass, propName));
/*    */   }
/*    */ 
/*    */   public String getValue(int row, int col, Object target)
/*    */   {
/*    */     try {
/* 26 */       Collection c = (Collection)this.prop.getReadMethod().invoke(target, new Object[0]);
/* 27 */       if (c == null)
/* 28 */         return null;
/* 29 */       this.lastValue = StringTools.concatStrings(c, "|");
/* 30 */       return (String)this.lastValue;
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/* 35 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.EmbeddedStringCollectionValueModel
 * JD-Core Version:    0.6.2
 */