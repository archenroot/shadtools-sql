/*    */ package com.shadworld.sql.tablemeta;
/*    */ 
/*    */ import com.shadworld.utils.StringTools;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class Index
/*    */ {
/* 12 */   Set<Column> columns = new LinkedHashSet();
/*    */   String name;
/* 14 */   IndexType type = IndexType.INDEX;
/* 15 */   IndexKind kind = IndexKind.DEFAULT;
/*    */ 
/*    */   public static Index PRIMARY_KEY(Column[] cols) {
/* 18 */     Index pk = new Index("pk", IndexType.PRIMARY, IndexKind.DEFAULT, cols);
/* 19 */     return pk;
/*    */   }
/*    */ 
/*    */   public Index(String name, IndexType type, IndexKind kind, Column[] columns)
/*    */   {
/* 24 */     this.columns.addAll(Arrays.asList(columns));
/* 25 */     this.name = name;
/* 26 */     this.type = type;
/* 27 */     this.kind = kind;
/*    */   }
/*    */ 
/*    */   public String buildCreate() {
/* 31 */     StringBuilder sb = new StringBuilder();
/* 32 */     if (this.type == IndexType.PRIMARY)
/* 33 */       sb.append(" PRIMARY KEY");
/* 34 */     if (this.type == IndexType.INDEX)
/* 35 */       sb.append(" INDEX");
/* 36 */     if (this.type == IndexType.UNIQUE)
/* 37 */       sb.append(" UNIQUE INDEX");
/* 38 */     if (this.type == IndexType.FULLTEXT)
/* 39 */       sb.append(" FULLTEXT INDEX");
/* 40 */     sb.append(" ").append(StringTools.wrap(this.name, '`')).append(" ");
/* 41 */     if (this.kind != IndexKind.DEFAULT) {
/* 42 */       sb.append("USING ").append(this.kind.toString());
/*    */     }
/* 44 */     sb.append("(");
/* 45 */     for (Iterator i = this.columns.iterator(); i.hasNext(); ) {
/* 46 */       sb.append(StringTools.wrap(((Column)i.next()).getDbColName(), '`'));
/* 47 */       if (i.hasNext())
/* 48 */         sb.append(", ");
/*    */     }
/* 50 */     sb.append(")");
/* 51 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static enum IndexKind
/*    */   {
/* 59 */     DEFAULT, BTREE, HASH, RTREE;
/*    */   }
/*    */ 
/*    */   public static enum IndexType
/*    */   {
/* 55 */     PRIMARY, UNIQUE, INDEX, FULLTEXT, SPATIAL;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.tablemeta.Index
 * JD-Core Version:    0.6.2
 */