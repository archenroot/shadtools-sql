/*     */ package com.shadworld.sql.orm;
/*     */ 
/*     */ import com.shadworld.sql.MySql;
/*     */ import com.shadworld.sql.ResultChooser;
/*     */ import com.shadworld.sql.Sql;
/*     */ import com.shadworld.sql.orm.naming.NamingStrategy;
/*     */ import com.shadworld.sql.tablemeta.TableMeta;
/*     */ import com.shadworld.utils.L;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class ORMBuilder
/*     */ {
/*     */   public static TableMeta tableFromBean(Class beanClass, String dbName, String dbUser, String dbPassword)
/*     */   {
/*  23 */     return tableFromBean(beanClass, dbName, BeanRegistry.get().getTableName(beanClass), dbUser, dbPassword);
/*     */   }
/*     */ 
/*     */   public static TableMeta tableFromBean(Class beanClass, String dbName, String dbTable, String dbUser, String dbPassword) {
/*  27 */     Sql sql = new MySql(dbName, dbUser, dbPassword);
/*  28 */     TableMeta meta = TableMeta.createFromBeanClass(beanClass, dbName, dbTable, new String[0]);
/*  29 */     sql.update(meta.buildCreate(true));
/*  30 */     sql.close();
/*  31 */     return meta;
/*     */   }
/*     */ 
/*     */   public static String beanFromTable(String dbName, String tableName, String dbUser, String dbPassword) {
/*  35 */     StringBuilder sb = new StringBuilder("\n/* auto-genned class code from table: ").append(tableName).append(" */\n\n");
/*     */ 
/*  38 */     sb.append("import ").append("javax.persistence.Entity").append(";\n");
/*  39 */     sb.append("import ").append("javax.persistence.Table").append(";\n");
/*  40 */     sb.append("import ").append("javax.persistence.Column").append(";\n");
/*  41 */     sb.append("import ").append("javax.persistence.Id").append(";\n");
/*  42 */     sb.append("\n");
/*     */ 
/*  44 */     String query = "SELECT * FROM " + tableName + " LIMIT 1;";
/*  45 */     Sql sql = new MySql(dbName, dbUser, dbPassword);
/*  46 */     L.println(query);
/*  47 */     ResultSet rs = sql.query(query).getResultSet();
/*  48 */     List colClassNames = new ArrayList();
/*  49 */     List colNames = new ArrayList();
/*     */     try {
/*  51 */       DatabaseMetaData dbmd = sql.getDataBaseMetaData();
/*  52 */       ResultSet pk = dbmd.getPrimaryKeys(dbName, dbName, tableName);
/*  53 */       List keyCols = new ArrayList();
/*  54 */       while (pk.next()) {
/*  55 */         keyCols.add(pk.getString(4));
/*     */       }
/*  57 */       ResultSetMetaData rsmd = rs.getMetaData();
/*  58 */       for (int i = 1; i < rsmd.getColumnCount(); i++) {
/*  59 */         String colClassName = rsmd.getColumnClassName(i);
/*  60 */         String colName = rsmd.getColumnName(i);
/*  61 */         colClassNames.add(colClassName);
/*  62 */         colNames.add(colName);
/*     */ 
/*  64 */         if (!colClassName.substring(0, colClassName.lastIndexOf(".")).equalsIgnoreCase("java.lang"))
/*     */         {
/*  66 */           String unqual = colClassName.substring(colClassName.lastIndexOf(".") + 1);
/*  67 */           if ((unqual.equals("Timestamp")) || (unqual.equals("Date")) || (unqual.equals("Time")))
/*  68 */             colClassName = "java.util.Date";
/*  69 */           sb.append("import ").append(colClassName).append(";\n");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  75 */       sb.append("\n@Entity\n@Table(name=\"").append(tableName).append("\")\n");
/*  76 */       sb.append("public class ").append(StringUtils.capitalize(StringTools.toCamelCase(tableName, "_")));
/*  77 */       sb.append(" {\n\n");
/*     */ 
/*  79 */       for (int i = 0; i < colClassNames.size(); i++) {
/*  80 */         if (keyCols.contains(colNames.get(i)))
/*  81 */           sb.append("\t@Id\n");
/*  82 */         String className = (String)colClassNames.get(i);
/*  83 */         className = className.substring(className.lastIndexOf(".") + 1);
/*     */ 
/*  85 */         if ((className.equals("Timestamp")) || (className.equals("Date")) || (className.equals("Time"))) {
/*  86 */           className = "Date";
/*     */         }
/*  88 */         String propName = BeanRegistry.get().getColumnPropertyName(null, (String)colNames.get(i));
/*  89 */         StringBuilder ab = new StringBuilder("\t@Column(");
/*  90 */         boolean addColumn = false;
/*     */ 
/*  92 */         if (!((String)colNames.get(i)).equalsIgnoreCase(BeanRegistry.get().getNamingStrategy(null).getPropertyColumnName(propName))) {
/*  93 */           if (addColumn)
/*  94 */             ab.append(", ");
/*  95 */           addColumn = true;
/*  96 */           ab.append("name=\"").append((String)colNames.get(i)).append("\"");
/*     */         }
/*     */ 
/*  99 */         if (rsmd.isNullable(i + 1) == 0)
/*     */         {
/* 101 */           if (addColumn)
/* 102 */             ab.append(", ");
/* 103 */           addColumn = true;
/* 104 */           ab.append("nullable=false");
/*     */         }
/* 106 */         if (addColumn) {
/* 107 */           sb.append(ab).append(")\n");
/*     */         }
/* 109 */         sb.append("\tprivate ").append(className).append(" ").append(propName).append(";\n");
/*     */       }
/*     */ 
/* 112 */       sb.append("\n}\n");
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 117 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 120 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.ORMBuilder
 * JD-Core Version:    0.6.2
 */