/*    */ package com.shadworld.sql;
/*    */ 
/*    */ import com.shadworld.sql.orm.JPAUtil;
/*    */ import com.shadworld.util.Time;
/*    */ import com.shadworld.utils.StringTools;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class SqlUtil
/*    */ {
/*    */   public static String classToTableName(Class type)
/*    */   {
/* 16 */     return StringTools.concatStrings(StringTools.splitCamelCase(type.getSimpleName()), "_");
/*    */   }
/*    */ 
/*    */   public static String propertyToColumnName(String propertyName) {
/* 20 */     return StringTools.concatStrings(StringTools.splitCamelCase(propertyName), "_");
/*    */   }
/*    */ 
/*    */   public static String propertyToColumnName(PropertyDescriptor property) {
/* 24 */     List parts = StringTools.splitCamelCase(property.getName());
/* 25 */     if (JPAUtil.isEntity(property.getPropertyType()))
/* 26 */       parts.add("id");
/* 27 */     return StringTools.concatStrings(parts, "_");
/*    */   }
/*    */ 
/*    */   public static String sqlEscape(String inString) {
/* 31 */     if (inString != null) {
/* 32 */       inString = inString.replace("\\", "\\\\");
/* 33 */       inString = inString.replace("'", "\\'");
/*    */     }
/* 35 */     return inString;
/*    */   }
/*    */ 
/*    */   public static String mysqlNow() {
/* 39 */     return Time.sqlDateTimeFormat.format(new Date());
/*    */   }
/*    */ 
/*    */   public static String getColumnName(ResultSetMetaData rsmd, int column) throws SQLException {
/* 43 */     String col = rsmd.getColumnLabel(column);
/* 44 */     if (col == null)
/* 45 */       col = rsmd.getColumnLabel(column);
/* 46 */     return col;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.SqlUtil
 * JD-Core Version:    0.6.2
 */