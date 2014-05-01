/*    */ package com.shadworld.sql;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.commons.dbcp.ConnectionFactory;
/*    */ import org.apache.commons.dbcp.DriverManagerConnectionFactory;
/*    */ import org.apache.commons.dbcp.PoolableConnectionFactory;
/*    */ import org.apache.commons.dbcp.PoolingDataSource;
/*    */ import org.apache.commons.pool.impl.GenericObjectPool;
/*    */ 
/*    */ public class MySql extends Sql
/*    */ {
/*    */   private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
/*    */ 
/*    */   protected void setDefaultOptions()
/*    */   {
/* 23 */     this.jdbcOptionMap.put("allowMultiQueries", "true");
/* 24 */     this.jdbcOptionMap.put("zeroDateTimeBehavior", "round");
/* 25 */     this.jdbcOptionMap.put("dumpQueriesOnException", "true");
/* 26 */     this.jdbcOptionMap.put("dumpMetadataOnColumnNotFound", "true");
/* 27 */     this.jdbcOptionMap.put("useCompression", "true");
/*    */   }
/*    */ 
/*    */   protected String buildUrl()
/*    */   {
/* 32 */     return "jdbc:mysql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName + getJdbcOptionsUrlString();
/*    */   }
/*    */ 
/*    */   protected String getDriverClass()
/*    */   {
/* 37 */     return "com.mysql.jdbc.Driver";
/*    */   }
/*    */ 
/*    */   public MySql(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword) {
/* 41 */     super(dbHost, dbPort, dbName, dbUser, dbPassword);
/*    */   }
/*    */ 
/*    */   public MySql(String dbName, String dbUser, String dbPassword)
/*    */   {
/* 46 */     super(dbName, dbUser, dbPassword);
/*    */   }
/*    */ 
/*    */   protected DataSource createDataSource()
/*    */   {
/* 52 */     GenericObjectPool connectionPool = new GenericObjectPool(null);
/* 53 */     ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(getUrl(), this.dbUser, this.dbPassword);
/* 54 */     PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
/* 55 */     PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
/* 56 */     connectionPool.setMaxActive(100);
/*    */ 
/* 66 */     return dataSource;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.MySql
 * JD-Core Version:    0.6.2
 */