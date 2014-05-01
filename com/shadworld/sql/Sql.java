/*     */ package com.shadworld.sql;
/*     */ 
/*     */ import com.shadworld.sql.dbutils.DataTableHandler;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.utils.L;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import javax.sql.DataSource;
/*     */ import org.apache.commons.dbcp.BasicDataSource;
/*     */ import org.apache.commons.dbutils.DbUtils;
/*     */ import org.apache.commons.dbutils.QueryRunner;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ 
/*     */ public abstract class Sql
/*     */ {
/*     */   protected Log log;
/*  41 */   protected String dbHost = "localhost";
/*  42 */   protected String dbPort = "3306";
/*     */   protected String dbName;
/*     */   protected String dbUser;
/*     */   protected String dbPassword;
/*  46 */   protected String jdbcOptions = null;
/*  47 */   protected LinkedHashMap<String, String> jdbcOptionMap = new LinkedHashMap();
/*  48 */   public boolean showQueries = false;
/*     */   public Connection conn;
/*     */   public Statement stmt;
/*     */   protected String tablePrefix;
/*     */   protected String url;
/*     */   private int rowCount;
/*  75 */   private static HashMap<String, DataSource> dataSources = new HashMap();
/*     */ 
/*     */   protected void setDefaultOptions()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected String getJdbcOptionsUrlString()
/*     */   {
/*  61 */     if (this.jdbcOptions != null)
/*  62 */       return this.jdbcOptions;
/*  63 */     StringBuilder sb = new StringBuilder(this.jdbcOptionMap.size() * 40);
/*     */ 
/*  65 */     for (Map.Entry option : this.jdbcOptionMap.entrySet()) {
/*  66 */       sb.append(sb.length() > 0 ? '&' : '?');
/*  67 */       sb.append((String)option.getKey());
/*  68 */       sb.append('=');
/*  69 */       sb.append((String)option.getValue());
/*     */     }
/*  71 */     this.jdbcOptions = sb.toString();
/*  72 */     return this.jdbcOptions;
/*     */   }
/*     */ 
/*     */   private DataSource getDataSource(String url)
/*     */   {
/*  84 */     DataSource ds = (DataSource)dataSources.get(url);
/*  85 */     if (ds == null) {
/*  86 */       ds = createDataSource();
/*  87 */       dataSources.put(url, ds);
/*     */     }
/*  89 */     return ds;
/*     */   }
/*     */ 
/*     */   protected DataSource createDataSource() {
/*  93 */     BasicDataSource d = new BasicDataSource();
/*     */ 
/*  95 */     d.setDriverClassName(getDriverClass());
/*  96 */     d.setUsername(this.dbUser);
/*  97 */     d.setPassword(this.dbPassword);
/*  98 */     d.setUrl(getUrl());
/*  99 */     return d;
/*     */   }
/*     */ 
/*     */   public void prepareConnection() throws SQLException
/*     */   {
/* 104 */     if ((this.conn == null) || (this.conn.isClosed())) {
/* 105 */       this.conn = getDataSource(getUrl()).getConnection();
/*     */     }
/* 107 */     if ((this.stmt == null) || (this.stmt.isClosed()))
/* 108 */       this.stmt = this.conn.createStatement(getResultSetType(), 1007);
/*     */   }
/*     */ 
/*     */   protected int getResultSetType()
/*     */   {
/* 118 */     return 1004;
/*     */   }
/*     */ 
/*     */   protected int getResultSetConcurrency() {
/* 122 */     return 1007;
/*     */   }
/*     */ 
/*     */   public String getUrl()
/*     */   {
/* 131 */     if (this.url == null)
/* 132 */       this.url = buildUrl();
/* 133 */     return this.url;
/*     */   }
/*     */ 
/*     */   protected abstract String getDriverClass();
/*     */ 
/*     */   public void init(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword, boolean createDBIfNotExists)
/*     */   {
/* 145 */     this.log = LogFactory.getLog(getClass());
/* 146 */     this.dbHost = dbHost;
/* 147 */     this.dbPort = dbPort;
/* 148 */     this.dbName = dbName;
/* 149 */     this.dbUser = dbUser;
/* 150 */     this.dbPassword = dbPassword;
/* 151 */     setDefaultOptions();
/*     */     try
/*     */     {
/* 154 */       Class.forName(getDriverClass());
/*     */     }
/*     */     catch (ClassNotFoundException e) {
/* 157 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract String buildUrl();
/*     */ 
/*     */   public Sql(String dbHost, String dbPort, String dbName, String dbUser, String dbPassword)
/*     */   {
/* 169 */     init(dbHost, dbPort, dbName, dbUser, dbPassword, false);
/*     */   }
/*     */ 
/*     */   public Sql(String dbName, String dbUser, String dbPassword) {
/* 173 */     init(this.dbHost, this.dbPort, dbName, dbUser, dbPassword, false);
/*     */   }
/*     */ 
/*     */   public Sql getCopy()
/*     */   {
/* 182 */     Sql sql = null;
/* 183 */     if ((this instanceof MySql))
/* 184 */       sql = new MySql(this.dbHost, this.dbPort, this.dbName, this.dbUser, this.dbPassword);
/* 185 */     return sql;
/*     */   }
/*     */ 
/*     */   protected void logQuery(String query) {
/* 189 */     if (this.showQueries)
/* 190 */       L.info(query);
/*     */   }
/*     */ 
/*     */   protected void logException(SQLException ex, String query) {
/* 194 */     this.log.error("SQLException: " + ex.getMessage());
/* 195 */     this.log.error("SQLState: " + ex.getSQLState());
/* 196 */     this.log.error("Message: " + ex.getMessage());
/* 197 */     this.log.error("Vendor error code: " + ex.getErrorCode());
/* 198 */     if (!this.showQueries)
/* 199 */       this.log.error("mysqlUpdate Q: " + query);
/*     */   }
/*     */ 
/*     */   private DataTable queryForTable(String query, Object[] params) {
/* 203 */     QueryRunner runner = new QueryRunner(getDataSource(getUrl()));
/* 204 */     logQuery(query);
/*     */     try {
/* 206 */       return (DataTable)runner.query(query, new DataTableHandler(false));
/*     */     }
/*     */     catch (SQLException localSQLException) {
/*     */     }
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   public <T> ResultChooser<T> queryOrFail(String query, Class<T> c) throws SQLException
/*     */   {
/* 215 */     ResultChooser chooser = new ResultChooser(null, c);
/*     */ 
/* 217 */     prepareConnection();
/*     */ 
/* 219 */     logQuery(query);
/* 220 */     ResultSet rs = this.stmt.executeQuery(query);
/* 221 */     chooser.rs = rs;
/* 222 */     return chooser;
/*     */   }
/*     */ 
/*     */   public ResultChooser queryOrFail(String query) throws SQLException {
/* 226 */     return queryOrFail(query, Object.class);
/*     */   }
/*     */ 
/*     */   public <T> ResultChooser<T> query(String query, Class<T> c)
/*     */   {
/* 238 */     QueryRunner runner = new QueryRunner(getDataSource(getUrl()));
/* 239 */     ResultChooser chooser = new ResultChooser(null, c);
/* 240 */     ResultSet rs = null;
/*     */     try {
/* 242 */       prepareConnection();
/*     */ 
/* 244 */       logQuery(query);
/* 245 */       rs = this.stmt.executeQuery(query);
/*     */     } catch (SQLException ex) {
/* 247 */       logException(ex, query);
/*     */     } catch (Exception e) {
/* 249 */       this.log.error(e + " THERE WAS A SCREWUP");
/* 250 */       if (!this.showQueries) {
/* 251 */         this.log.error("Q: " + query);
/*     */       }
/*     */     }
/* 254 */     chooser.rs = rs;
/* 255 */     return chooser;
/*     */   }
/*     */ 
/*     */   public ResultChooser query(String query)
/*     */   {
/* 267 */     return query(query, Object.class);
/*     */   }
/*     */ 
/*     */   public <T> ResultChooser<T> queryQuietly(String query, Class<T> c)
/*     */   {
/* 279 */     QueryRunner runner = new QueryRunner(getDataSource(getUrl()));
/* 280 */     ResultChooser chooser = new ResultChooser(null, c);
/* 281 */     ResultSet rs = null;
/*     */     try {
/* 283 */       prepareConnection();
/*     */ 
/* 285 */       logQuery(query);
/* 286 */       rs = this.stmt.executeQuery(query);
/*     */     } catch (Exception localException) {
/*     */     }
/* 289 */     chooser.rs = rs;
/* 290 */     return chooser;
/*     */   }
/*     */ 
/*     */   public ResultChooser queryQuietly(String query)
/*     */   {
/* 302 */     return queryQuietly(query, Object.class);
/*     */   }
/*     */ 
/*     */   public List<Integer> insertOrFail(String query)
/*     */     throws SQLException
/*     */   {
/* 311 */     prepareConnection();
/*     */ 
/* 313 */     logQuery(query);
/* 314 */     int updates = this.stmt.executeUpdate(query, 1);
/* 315 */     ResultSet rs = this.stmt.getGeneratedKeys();
/* 316 */     ArrayList keys = new ArrayList();
/* 317 */     while (rs.next())
/* 318 */       keys.add(Integer.valueOf(rs.getInt(1)));
/* 319 */     return keys;
/*     */   }
/*     */ 
/*     */   public List<Integer> insert(String query)
/*     */     throws SQLException
/*     */   {
/*     */     try
/*     */     {
/* 329 */       return insertOrFail(query);
/*     */     } catch (SQLException ex) {
/* 331 */       logException(ex, query);
/*     */     } catch (Exception e) {
/* 333 */       this.log.error(e + " THERE WAS A SCREWUP");
/* 334 */       this.log.error("Q: " + query);
/*     */     }
/*     */ 
/* 337 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public List<Integer> insertQuietly(String query)
/*     */   {
/*     */     try
/*     */     {
/* 349 */       return insertOrFail(query);
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 353 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public int updateOrFail(String query)
/*     */     throws SQLException
/*     */   {
/* 362 */     prepareConnection();
/*     */ 
/* 364 */     logQuery(query);
/* 365 */     return this.stmt.executeUpdate(query);
/*     */   }
/*     */ 
/*     */   public int updateOrFailForKeys(String query)
/*     */     throws SQLException
/*     */   {
/* 374 */     prepareConnection();
/*     */ 
/* 377 */     logQuery(query);
/* 378 */     this.stmt.clearBatch();
/* 379 */     return this.stmt.executeUpdate(query, 1);
/*     */   }
/*     */ 
/*     */   public int update(String query)
/*     */   {
/*     */     try
/*     */     {
/* 390 */       return updateOrFail(query);
/*     */     } catch (SQLException ex) {
/* 392 */       logException(ex, query);
/*     */     } catch (Exception e) {
/* 394 */       this.log.error(e + " THERE WAS A SCREWUP");
/* 395 */       this.log.error("Q: " + query);
/*     */     }
/*     */ 
/* 398 */     return -1;
/*     */   }
/*     */ 
/*     */   public int updateForKeys(String query)
/*     */   {
/*     */     try
/*     */     {
/* 409 */       return updateOrFailForKeys(query);
/*     */     } catch (SQLException ex) {
/* 411 */       logException(ex, query);
/*     */     } catch (Exception e) {
/* 413 */       this.log.error(e + " THERE WAS A SCREWUP");
/* 414 */       this.log.error("Q: " + query);
/*     */     }
/*     */ 
/* 417 */     return -1;
/*     */   }
/*     */ 
/*     */   public Integer getLastInsertId()
/*     */     throws SQLException
/*     */   {
/* 426 */     ResultSet keys = this.stmt.getGeneratedKeys();
/* 427 */     keys.next();
/* 428 */     Integer lastInsertId = Integer.valueOf(keys.getInt(1));
/* 429 */     keys.close();
/* 430 */     return lastInsertId;
/*     */   }
/*     */ 
/*     */   public List<Integer> getLastInsertIds()
/*     */     throws SQLException
/*     */   {
/* 438 */     List lastInsertIds = new ArrayList();
/* 439 */     ResultSet keys = this.stmt.getGeneratedKeys();
/* 440 */     while (keys.next())
/* 441 */       lastInsertIds.add(Integer.valueOf(keys.getInt(1)));
/* 442 */     keys.close();
/*     */ 
/* 444 */     return lastInsertIds;
/*     */   }
/*     */ 
/*     */   public void close() {
/* 448 */     DbUtils.closeQuietly(this.stmt);
/* 449 */     DbUtils.closeQuietly(this.conn);
/*     */   }
/*     */ 
/*     */   public void startTransaction() throws SQLException {
/* 453 */     prepareConnection();
/* 454 */     this.conn.setAutoCommit(false);
/*     */   }
/*     */ 
/*     */   public void commit() throws SQLException
/*     */   {
/* 459 */     prepareConnection();
/* 460 */     this.conn.commit();
/*     */   }
/*     */ 
/*     */   public void rollback() throws SQLException {
/* 464 */     prepareConnection();
/* 465 */     this.conn.rollback();
/*     */   }
/*     */ 
/*     */   public int updateQuietly(String query)
/*     */   {
/*     */     try
/*     */     {
/* 477 */       return updateOrFail(query);
/*     */     }
/*     */     catch (Exception localException) {
/*     */     }
/* 481 */     return -1;
/*     */   }
/*     */ 
/*     */   public String getDbHost()
/*     */   {
/* 488 */     return this.dbHost;
/*     */   }
/*     */ 
/*     */   public void setDbHost(String dbHost)
/*     */   {
/* 496 */     this.dbHost = dbHost;
/*     */   }
/*     */ 
/*     */   public String getDbPort()
/*     */   {
/* 503 */     return this.dbPort;
/*     */   }
/*     */ 
/*     */   public void setDbPort(String dbPort)
/*     */   {
/* 511 */     this.dbPort = dbPort;
/*     */   }
/*     */ 
/*     */   public String getDbName()
/*     */   {
/* 518 */     return this.dbName;
/*     */   }
/*     */ 
/*     */   public void setDbName(String dbName)
/*     */   {
/* 526 */     this.dbName = dbName;
/*     */   }
/*     */ 
/*     */   public String getDbUser()
/*     */   {
/* 533 */     return this.dbUser;
/*     */   }
/*     */ 
/*     */   public void setDbUser(String dbUser)
/*     */   {
/* 541 */     this.dbUser = dbUser;
/*     */   }
/*     */ 
/*     */   public String getDbPassword()
/*     */   {
/* 548 */     return this.dbPassword;
/*     */   }
/*     */ 
/*     */   public void setDbPassword(String dbPassword)
/*     */   {
/* 556 */     this.dbPassword = dbPassword;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setJdbcOptions(String jdbcOptions)
/*     */   {
/* 573 */     this.jdbcOptions = jdbcOptions;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<String, String> getJdbcOptionMap()
/*     */   {
/* 582 */     this.jdbcOptions = null;
/* 583 */     this.url = null;
/* 584 */     return this.jdbcOptionMap;
/*     */   }
/*     */ 
/*     */   public void setJdbcOptionMap(LinkedHashMap<String, String> jdbcOptionMap)
/*     */   {
/* 591 */     this.jdbcOptions = null;
/* 592 */     this.jdbcOptionMap = jdbcOptionMap;
/*     */   }
/*     */ 
/*     */   public String getTablePrefix()
/*     */   {
/* 599 */     return this.tablePrefix;
/*     */   }
/*     */ 
/*     */   public void setTablePrefix(String tablePrefix)
/*     */   {
/* 607 */     this.tablePrefix = tablePrefix;
/*     */   }
/*     */ 
/*     */   public DatabaseMetaData getDataBaseMetaData() throws SQLException {
/* 611 */     DatabaseMetaData dbmd = this.conn.getMetaData();
/* 612 */     return dbmd;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.Sql
 * JD-Core Version:    0.6.2
 */