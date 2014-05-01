/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import com.shadworld.sql.orm.BeanRegistry;
/*    */ import com.shadworld.sql.query.column.ColumnSelection;
/*    */ import com.shadworld.struct.DataTable;
/*    */ import com.shadworld.struct.Record;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Insert extends AbstractInsert<Insert>
/*    */ {
/*    */   public Insert(DataTable data, ColumnSelection colNames, String tableName)
/*    */   {
/* 21 */     super(data, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Insert(Collection<Object> beans, ColumnSelection colNames, String tableName)
/*    */   {
/* 26 */     super(beans, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Insert(Object bean, ColumnSelection colNames, String tableName)
/*    */   {
/* 31 */     super(bean, colNames, tableName);
/*    */   }
/*    */ 
/*    */   public Insert(Record rec, ColumnSelection colNames, String tableName)
/*    */   {
/* 36 */     super(rec, colNames, tableName);
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 44 */     return "INSERT INTO ";
/*    */   }
/*    */ 
/*    */   public OnDuplicateKeyUpdate onDuplicateKeyUpdate()
/*    */   {
/* 59 */     ColumnSelection cols = null;
/* 60 */     if (this.colSel != null) {
/* 61 */       cols = this.colSel.clone();
/* 62 */       List idProps = BeanRegistry.get().getIdPropertiesOrDefault(this.dataClass);
/* 63 */       List names = new ArrayList();
/* 64 */       for (PropertyDescriptor prop : idProps)
/* 65 */         names.add(prop.getName());
/* 66 */       cols.removeCols(names);
/*    */     }
/* 68 */     OnDuplicateKeyUpdate dupKey = new OnDuplicateKeyUpdate(this, cols);
/* 69 */     this.clauses.put(OnDuplicateKeyUpdate.class, dupKey);
/* 70 */     return dupKey;
/*    */   }
/*    */ 
/*    */   public OnDuplicateKeyUpdate onDuplicateKeyUpdate(String element) {
/* 74 */     OnDuplicateKeyUpdate dupKey = new OnDuplicateKeyUpdate(this, element);
/* 75 */     this.clauses.put(OnDuplicateKeyUpdate.class, dupKey);
/* 76 */     return dupKey;
/*    */   }
/*    */ 
/*    */   public OnDuplicateKeyUpdate onDuplicateKeyUpdate(String[] elements) {
/* 80 */     OnDuplicateKeyUpdate dupKey = new OnDuplicateKeyUpdate(this, elements);
/* 81 */     this.clauses.put(OnDuplicateKeyUpdate.class, dupKey);
/* 82 */     return dupKey;
/*    */   }
/*    */ 
/*    */   public OnDuplicateKeyUpdate onDuplicateKeyUpdate(Collection<String> elements) {
/* 86 */     OnDuplicateKeyUpdate dupKey = new OnDuplicateKeyUpdate(this, elements);
/* 87 */     this.clauses.put(OnDuplicateKeyUpdate.class, dupKey);
/* 88 */     return dupKey;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Insert
 * JD-Core Version:    0.6.2
 */