/*     */ package com.shadworld.sql;
/*     */ 
/*     */ import com.shadworld.sql.dbutils.DataTableHandler;
/*     */ import com.shadworld.sql.dbutils.RecordHandler;
/*     */ import com.shadworld.sql.dbutils.ShadBeanProcessor;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ResultChooser<T>
/*     */ {
/*     */   ResultSet rs;
/*     */   ShadBeanProcessor beanProcessor;
/*     */   Integer lastInsertId;
/*     */   List<Integer> lastInsertIds;
/*     */ 
/*     */   public ResultChooser(ResultSet resultSet, Class<? extends Object> resultType)
/*     */   {
/*  24 */     this.rs = resultSet;
/*     */   }
/*     */ 
/*     */   public ResultSet getResultSet()
/*     */   {
/*  31 */     return this.rs;
/*     */   }
/*     */ 
/*     */   public DataTable getDataTable() throws SQLException {
/*  35 */     DataTableHandler dataTableHandler = new DataTableHandler(false);
/*  36 */     this.beanProcessor = dataTableHandler.getProcessor();
/*  37 */     return dataTableHandler.handle(this.rs);
/*     */   }
/*     */ 
/*     */   public Record getRecord() throws SQLException {
/*  41 */     RecordHandler recordHandler = new RecordHandler(false);
/*  42 */     this.beanProcessor = recordHandler.getProcessor();
/*  43 */     return recordHandler.handle(this.rs);
/*     */   }
/*     */ 
/*     */   public T getBean(Class<T> type) throws SQLException {
/*  47 */     this.beanProcessor = new ShadBeanProcessor();
/*  48 */     return this.beanProcessor.toBean(this.rs, type);
/*     */   }
/*     */ 
/*     */   public Object[] getBeans(Class<T>[] types) throws SQLException
/*     */   {
/*  53 */     this.beanProcessor = new ShadBeanProcessor();
/*  54 */     return this.beanProcessor.toBeans(this.rs, types);
/*     */   }
/*     */ 
/*     */   public List<T> getBeanList(Class<T> type) throws SQLException {
/*  58 */     this.beanProcessor = new ShadBeanProcessor();
/*  59 */     return this.beanProcessor.toBeanList(this.rs, type);
/*     */   }
/*     */ 
/*     */   public List<Object>[] getBeanLists(Class<T>[] types)
/*     */     throws SQLException
/*     */   {
/*  75 */     this.beanProcessor = new ShadBeanProcessor();
/*  76 */     return this.beanProcessor.toBeanLists(this.rs, types);
/*     */   }
/*     */ 
/*     */   public List<Object[]> getBeansList(Class<T>[] types)
/*     */     throws SQLException
/*     */   {
/*  91 */     this.beanProcessor = new ShadBeanProcessor();
/*  92 */     return this.beanProcessor.toBeansList(this.rs, types);
/*     */   }
/*     */ 
/*     */   public ShadBeanProcessor getBeanProcessor()
/*     */   {
/*  99 */     return this.beanProcessor;
/*     */   }
/*     */ 
/*     */   public Record getMissedProperties(Object bean) {
/* 103 */     return (Record)this.beanProcessor.getMissedProperties().get(bean);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Integer getLastInsertId()
/*     */   {
/* 111 */     if (this.lastInsertId == null) {
/*     */       try
/*     */       {
/* 114 */         Statement stmt = this.rs.getStatement();
/* 115 */         ResultSet keys = stmt.getGeneratedKeys();
/* 116 */         keys.next();
/* 117 */         this.lastInsertId = Integer.valueOf(keys.getInt(1));
/* 118 */         keys.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 122 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 125 */     return this.lastInsertId;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public List<Integer> getLastInsertIds()
/*     */   {
/* 133 */     if (this.lastInsertIds == null) {
/* 134 */       this.lastInsertIds = new ArrayList();
/*     */       try {
/* 136 */         Statement stmt = this.rs.getStatement();
/* 137 */         ResultSet keys = stmt.getGeneratedKeys();
/* 138 */         while (keys.next())
/* 139 */           this.lastInsertIds.add(Integer.valueOf(keys.getInt(1)));
/* 140 */         keys.close();
/*     */       }
/*     */       catch (SQLException e)
/*     */       {
/* 144 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 147 */     return this.lastInsertIds;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.ResultChooser
 * JD-Core Version:    0.6.2
 */