/*    */ package com.shadworld.sql.orm.naming;
/*    */ 
/*    */ import com.shadworld.sql.SqlUtil;
/*    */ import com.shadworld.utils.StringTools;
/*    */ import java.beans.PropertyDescriptor;
/*    */ 
/*    */ public class CamelCaseToUnderscoreNamingStrategy extends NamingStrategy
/*    */ {
/*    */   public CamelCaseToUnderscoreNamingStrategy(Class clazz)
/*    */   {
/* 14 */     super(clazz);
/*    */   }
/*    */ 
/*    */   public void cache()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String getPropertyColumnName(PropertyDescriptor property)
/*    */   {
/* 35 */     return SqlUtil.propertyToColumnName(property);
/*    */   }
/*    */ 
/*    */   public String getPropertyColumnName(String propName)
/*    */   {
/* 45 */     return SqlUtil.propertyToColumnName(propName);
/*    */   }
/*    */ 
/*    */   public String getTableName(Class c)
/*    */   {
/* 53 */     return SqlUtil.classToTableName(c);
/*    */   }
/*    */ 
/*    */   public String getColumnPropertyName(String columnName)
/*    */   {
/* 61 */     return StringTools.toCamelCase(columnName, "_");
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.naming.CamelCaseToUnderscoreNamingStrategy
 * JD-Core Version:    0.6.2
 */