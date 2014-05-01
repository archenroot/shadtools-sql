/*    */ package com.shadworld.sql.dbutils;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class MultiBeanHandler extends ShadHandler<Object[]>
/*    */ {
/*    */   Class[] classes;
/*    */   Object[] beans;
/*    */ 
/*    */   public MultiBeanHandler(Class[] classes)
/*    */   {
/* 16 */     super(true);
/* 17 */     this.classes = classes;
/*    */   }
/*    */ 
/*    */   public MultiBeanHandler(Object[] beans) {
/* 21 */     super(true);
/* 22 */     this.beans = beans;
/* 23 */     for (Object bean : beans)
/* 24 */       if (bean == null)
/* 25 */         throw new IllegalArgumentException("cannot pass null beans to this constructor");
/*    */   }
/*    */ 
/*    */   public Object[] handle(ResultSet rs) throws SQLException
/*    */   {
/* 30 */     if (this.classes != null) {
/* 31 */       this.beans = new Object[this.classes.length];
/* 32 */       for (int i = 0; i < this.beans.length; i++) {
/* 33 */         this.beans[i] = this.processor.toBean(rs, this.classes[i]);
/*    */       }
/* 35 */       return this.beans;
/*    */     }
/* 37 */     for (int i = 0; i < this.beans.length; i++) {
/* 38 */       this.beans[i] = this.processor.toBean(rs, this.beans[i]);
/*    */     }
/* 40 */     return this.beans;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.MultiBeanHandler
 * JD-Core Version:    0.6.2
 */