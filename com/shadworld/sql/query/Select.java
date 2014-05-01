/*    */ package com.shadworld.sql.query;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Select extends Q<Select>
/*    */ {
/*  7 */   String option = null;
/*  8 */   String body = null;
/*  9 */   boolean forUpdate = false;
/*    */   private static final String SELECT = "SELECT ";
/*    */ 
/*    */   public Select(String body)
/*    */   {
/* 15 */     this(null, body);
/*    */   }
/*    */ 
/*    */   public Select(String option, String body) {
/* 19 */     super(null);
/* 20 */     this.option = option;
/* 21 */     if (body != null)
/* 22 */       a(body);
/*    */     else {
/* 24 */       a("*");
/*    */     }
/* 26 */     this.rootQuery = this;
/*    */   }
/*    */ 
/*    */   public From from() {
/* 30 */     From from = (From)getRootQuery().clauses.get(From.class);
/* 31 */     if (from == null) {
/* 32 */       from = new From(this);
/*    */     }
/* 34 */     return from;
/*    */   }
/*    */ 
/*    */   public From from(String e) {
/* 38 */     From from = (From)getRootQuery().clauses.get(From.class);
/* 39 */     if (from == null) {
/* 40 */       from = new From(this);
/*    */     }
/* 42 */     from.a(e);
/* 43 */     return from;
/*    */   }
/*    */ 
/*    */   protected StringBuilder buildChildren(StringBuilder sb)
/*    */   {
/* 49 */     sb.append("\n");
/* 50 */     sb.append(((Q)this.clauses.get(From.class)).buildInternal());
/* 51 */     if (this.clauses.get(Where.class) != null) {
/* 52 */       sb.append("\n").append(((Q)this.clauses.get(Where.class)).buildInternal());
/*    */     }
/* 54 */     if (this.clauses.get(GroupBy.class) != null) {
/* 55 */       sb.append("\n").append(((Q)this.clauses.get(GroupBy.class)).buildInternal());
/*    */     }
/* 57 */     if (this.clauses.get(Having.class) != null) {
/* 58 */       sb.append("\n").append(((Q)this.clauses.get(Having.class)).buildInternal());
/*    */     }
/* 60 */     if (this.clauses.get(OrderBy.class) != null) {
/* 61 */       sb.append("\n").append(((Q)this.clauses.get(OrderBy.class)).buildInternal());
/*    */     }
/* 63 */     if (this.clauses.get(Limit.class) != null) {
/* 64 */       sb.append("\n").append(((Q)this.clauses.get(Limit.class)).buildInternal());
/*    */     }
/* 66 */     if (this.forUpdate) {
/* 67 */       sb.append("\n").append(" FOR UPDATE");
/*    */     }
/* 69 */     return sb;
/*    */   }
/*    */ 
/*    */   protected StringBuilder buildInternal()
/*    */   {
/* 77 */     if (this.clauses.size() == 0)
/* 78 */       a("*");
/* 79 */     return super.buildInternal();
/*    */   }
/*    */ 
/*    */   protected String getPrefix()
/*    */   {
/* 84 */     if (this.option == null)
/* 85 */       return "SELECT ";
/* 86 */     return "SELECT " + this.option + " ";
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.Select
 * JD-Core Version:    0.6.2
 */