/*     */ package com.shadworld.sql.orm;
/*     */ 
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class BeanIndex
/*     */ {
/*  13 */   private HashMap<Class, HashMap<String, Object>> beanIndexes = new HashMap();
/*  14 */   private HashMap<Class, PropertyIndex> propertyIndexes = new HashMap();
/*     */ 
/*     */   public <B> B indexBean(Class type, B bean)
/*     */   {
/*  23 */     return indexBean(type, bean, false);
/*     */   }
/*     */ 
/*     */   public <B> B indexBean(B bean)
/*     */   {
/*  32 */     return indexBean(bean.getClass(), bean, false);
/*     */   }
/*     */ 
/*     */   public <B> B indexBean(B bean, boolean forcePut)
/*     */   {
/*  42 */     return indexBean(bean.getClass(), bean, forcePut);
/*     */   }
/*     */ 
/*     */   public <B> B indexBean(Class type, B bean, boolean forcePut)
/*     */   {
/*  53 */     HashMap beanIndex = getBeanIndex(type);
/*  54 */     String indexKey = BeanRegistry.get().getBeanPrimaryKey(bean);
/*  55 */     if (forcePut) {
/*  56 */       beanIndex.put(indexKey, bean);
/*  57 */       return bean;
/*     */     }
/*  59 */     Object oldBean = beanIndex.get(indexKey);
/*  60 */     if (oldBean == null) {
/*  61 */       beanIndex.put(indexKey, bean);
/*  62 */       return bean;
/*     */     }
/*  64 */     return oldBean;
/*     */   }
/*     */ 
/*     */   public boolean isIndexed(Object bean)
/*     */   {
/*  74 */     if (bean == null)
/*  75 */       return false;
/*  76 */     return getBean(bean.getClass(), BeanRegistry.get().getBeanPrimaryKey(bean)) != null;
/*     */   }
/*     */ 
/*     */   public <B> B getBean(Class<B> type, String indexKey)
/*     */   {
/*  87 */     return getBeanIndex(type).get(indexKey);
/*     */   }
/*     */ 
/*     */   public boolean isInstanceIndexed(Object bean)
/*     */   {
/*  96 */     if (bean == null)
/*  97 */       return false;
/*  98 */     return getBean(bean.getClass(), BeanRegistry.get().getBeanPrimaryKey(bean)) == bean;
/*     */   }
/*     */ 
/*     */   public <B> B deIndex(B bean) {
/* 102 */     if (bean == null)
/* 103 */       return null;
/* 104 */     return getBeanIndex(bean.getClass()).remove(BeanRegistry.get().getBeanPrimaryKey(bean));
/*     */   }
/*     */ 
/*     */   private HashMap<String, Object> getBeanIndex(Class type) {
/* 108 */     HashMap beanMap = (HashMap)this.beanIndexes.get(type);
/* 109 */     if (beanMap == null) {
/* 110 */       beanMap = new HashMap();
/* 111 */       this.beanIndexes.put(type, beanMap);
/*     */     }
/* 113 */     return beanMap;
/*     */   }
/*     */ 
/*     */   public void addPropertyIndex(Class type, String propName)
/*     */   {
/* 122 */     PropertyIndex index = (PropertyIndex)this.propertyIndexes.get(type);
/* 123 */     if (index == null) {
/* 124 */       index = new PropertyIndex(type, null);
/* 125 */       this.propertyIndexes.put(type, index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PropertyIndex
/*     */   {
/*     */     private Class type;
/* 139 */     private HashMap<String, TreeMap<Object, Set<Object>>> propertyIndex = new HashMap();
/*     */ 
/*     */     private PropertyIndex(Class type) {
/* 142 */       this.type = type;
/*     */     }
/*     */ 
/*     */     private void indexProperty(PropertyDescriptor prop, Object bean)
/*     */     {
/* 154 */       Method getter = prop.getReadMethod();
/*     */       try {
/* 156 */         value = getter.invoke(bean, new Object[0]);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */         Object value;
/* 161 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.BeanIndex
 * JD-Core Version:    0.6.2
 */