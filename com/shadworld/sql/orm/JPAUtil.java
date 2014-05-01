/*    */ package com.shadworld.sql.orm;
/*    */ 
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.persistence.Column;
/*    */ import javax.persistence.Entity;
/*    */ import javax.persistence.Id;
/*    */ import javax.persistence.ManyToOne;
/*    */ import javax.persistence.OneToOne;
/*    */ import javax.persistence.Table;
/*    */ import javax.persistence.UniqueConstraint;
/*    */ 
/*    */ public class JPAUtil
/*    */ {
/*    */   public static boolean isEntity(Class c)
/*    */   {
/* 21 */     return c.getAnnotation(Entity.class) != null;
/*    */   }
/*    */ 
/*    */   public static List<PropertyDescriptor> getPrimaryKeyProperties(Class c, PropertyDescriptor[] props) {
/* 25 */     List ids = new ArrayList();
/* 26 */     for (PropertyDescriptor prop : props) {
/* 27 */       Id id = (Id)findAnnotationForProperty(Id.class, c, prop);
/* 28 */       if (id != null) {
/* 29 */         ids.add(prop);
/*    */       }
/*    */     }
/* 32 */     return ids;
/*    */   }
/*    */ 
/*    */   public static List<String[]> getUniqueContraints(Class c)
/*    */   {
/* 41 */     List constraints = new ArrayList();
/* 42 */     Table table = (Table)c.getAnnotation(Table.class);
/* 43 */     if (table != null) {
/* 44 */       for (UniqueConstraint constraint : table.uniqueConstraints()) {
/* 45 */         constraints.add(constraint.columnNames());
/*    */       }
/*    */     }
/* 48 */     return constraints;
/*    */   }
/*    */ 
/*    */   public static String getTableName(Class c) {
/* 52 */     Table table = (Table)c.getAnnotation(Table.class);
/* 53 */     if (table == null)
/* 54 */       return null;
/* 55 */     return table.name();
/*    */   }
/*    */ 
/*    */   public static String getColumnName(Class c, PropertyDescriptor property) {
/* 59 */     Method m = property.getReadMethod();
/* 60 */     Column column = (Column)findAnnotationForProperty(Column.class, c, property);
/* 61 */     if (column != null)
/* 62 */       return column.name();
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   public static Annotation findJPAMappingClass(Class c, PropertyDescriptor property) {
/* 67 */     OneToOne oneToOne = (OneToOne)findAnnotationForProperty(OneToOne.class, c, property);
/* 68 */     if (oneToOne != null)
/* 69 */       return oneToOne;
/* 70 */     ManyToOne manyToOne = (ManyToOne)findAnnotationForProperty(ManyToOne.class, c, property);
/* 71 */     if (manyToOne != null)
/* 72 */       return manyToOne;
/* 73 */     return null;
/*    */   }
/*    */ 
/*    */   public static Annotation findAnnotationForProperty(Class<? extends Annotation> a, Class c, PropertyDescriptor property)
/*    */   {
/* 83 */     Annotation result = null;
/* 84 */     Method m = property.getReadMethod();
/* 85 */     if (m != null)
/* 86 */       result = m.getAnnotation(a);
/* 87 */     if (result == null)
/*    */       try {
/* 89 */         Field f = c.getDeclaredField(property.getName());
/* 90 */         if (f != null)
/* 91 */           result = f.getAnnotation(a);
/*    */       } catch (SecurityException e) {
/* 93 */         e.printStackTrace();
/*    */       }
/*    */       catch (NoSuchFieldException localNoSuchFieldException)
/*    */       {
/*    */       }
/* 98 */     return result;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.JPAUtil
 * JD-Core Version:    0.6.2
 */