/*    */ package com.shadworld.sql.orm.naming;
/*    */ 
/*    */ import java.beans.PropertyDescriptor;
/*    */ 
/*    */ public class CompoundNamingStrategy extends NamingStrategy
/*    */ {
/*    */   NamingStrategy[] strategies;
/*    */ 
/*    */   public CompoundNamingStrategy(Class clazz, NamingStrategy[] strategies)
/*    */   {
/* 13 */     super(clazz);
/* 14 */     this.strategies = strategies;
/*    */   }
/*    */ 
/*    */   public void cache()
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean isColumnMatch(String columnName, PropertyDescriptor property)
/*    */   {
/* 34 */     for (int i = 0; i < this.strategies.length; i++) {
/* 35 */       if (this.strategies[i].isColumnMatch(columnName, property))
/* 36 */         return true;
/*    */     }
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isTableMatch(String tableName, Class beanClass)
/*    */   {
/* 43 */     for (int i = 0; i < this.strategies.length; i++) {
/* 44 */       if (this.strategies[i].isTableMatch(tableName, beanClass))
/* 45 */         return true;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public String getPropertyColumnName(PropertyDescriptor property)
/*    */   {
/* 55 */     return this.strategies[0].getPropertyColumnName(property);
/*    */   }
/*    */ 
/*    */   public String getPropertyColumnName(String propName)
/*    */   {
/* 65 */     return this.strategies[0].getPropertyColumnName(propName);
/*    */   }
/*    */ 
/*    */   public String getTableName(Class c)
/*    */   {
/* 74 */     return this.strategies[0].getTableName(c);
/*    */   }
/*    */ 
/*    */   public String getColumnPropertyName(String columnName)
/*    */   {
/* 82 */     return this.strategies[0].getColumnPropertyName(columnName);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.naming.CompoundNamingStrategy
 * JD-Core Version:    0.6.2
 */