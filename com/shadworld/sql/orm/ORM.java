/*    */ package com.shadworld.sql.orm;
/*    */ 
/*    */ import com.shadworld.sql.ResultChooser;
/*    */ import com.shadworld.sql.Sql;
/*    */ import com.shadworld.utils.L;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ORM
/*    */ {
/*    */   public static <T> T loadBean(Class<T> c, Sql sql, String query)
/*    */   {
/*    */     try
/*    */     {
/* 13 */       return sql.query(query, c).getBean(c);
/*    */     } catch (SQLException e) {
/* 15 */       L.warn(query, e);
/* 16 */     }return null;
/*    */   }
/*    */ 
/*    */   public static <T> List<T> loadBeanList(Class<T> c, Sql sql, String query)
/*    */   {
/*    */     try {
/* 22 */       return sql.query(query, c).getBeanList(c);
/*    */     } catch (SQLException e) {
/* 24 */       L.warn(query, e);
/* 25 */     }return null;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.ORM
 * JD-Core Version:    0.6.2
 */