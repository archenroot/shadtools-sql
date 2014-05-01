/*    */ package com.shadworld.sql.orm.naming;
/*    */ 
/*    */ import com.shadworld.sql.orm.BeanRegistry;
/*    */ import java.beans.PropertyDescriptor;
/*    */ 
/*    */ public abstract class NamingStrategy
/*    */ {
/*    */   Class clazz;
/*    */ 
/*    */   public NamingStrategy(Class clazz)
/*    */   {
/* 14 */     this.clazz = clazz;
/* 15 */     cache();
/*    */   }
/*    */ 
/*    */   public abstract void cache();
/*    */ 
/*    */   public boolean isColumnMatch(String columnName, PropertyDescriptor property)
/*    */   {
/* 24 */     String colPropColName = getPropertyColumnName(property);
/* 25 */     if (colPropColName.equalsIgnoreCase(columnName))
/* 26 */       return true;
/* 27 */     if ((("is_" + colPropColName).equalsIgnoreCase(columnName)) && ((Boolean.TYPE == property.getPropertyType()) || (Boolean.class == property.getPropertyType())))
/* 28 */       return true;
/* 29 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isTableMatch(String tableName, Class beanClass) {
/* 33 */     return BeanRegistry.get().getTableName(beanClass).equalsIgnoreCase(tableName);
/*    */   }
/*    */ 
/*    */   public abstract String getTableName(Class paramClass);
/*    */ 
/*    */   public abstract String getPropertyColumnName(PropertyDescriptor paramPropertyDescriptor);
/*    */ 
/*    */   public abstract String getPropertyColumnName(String paramString);
/*    */ 
/*    */   public abstract String getColumnPropertyName(String paramString);
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.naming.NamingStrategy
 * JD-Core Version:    0.6.2
 */