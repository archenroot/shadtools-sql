/*     */ package com.shadworld.sql;
/*     */ 
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.struct.transform.DataTransform;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ssTools
/*     */ {
/*     */   public static final int LOG_LEVEL_SYSTEM = 0;
/*     */   public static final int LOG_LEVEL_DEBUG = 1;
/*     */   public static final int LOG_LEVEL_INFO = 2;
/*     */   public static final int LOG_LEVEL_WARN = 3;
/*     */   public static final int LOG_LEVEL_ERROR = 4;
/*     */   public static final int LOG_LEVEL_TIME = 5;
/*     */   public static final int LOG_LEVEL_DATETIME = 6;
/*     */   public static final int LOG_LEVEL_RUNTIME = 7;
/*  37 */   public Boolean insideScraper = Boolean.valueOf(false);
/*  38 */   public Integer defaultLogLevel = Integer.valueOf(0);
/*  39 */   public boolean printLevelDefault = true;
/*  40 */   private Integer markCounter = Integer.valueOf(0);
/*     */ 
/*     */   private void ssTools(int defaultLogLevel) {
/*  43 */     this.insideScraper = Boolean.valueOf(true);
/*  44 */     this.defaultLogLevel = Integer.valueOf(defaultLogLevel);
/*     */   }
/*     */ 
/*     */   private void logCommon(String logText, Integer logLevel, boolean printLevel)
/*     */   {
/*  52 */     switch (logLevel.intValue()) {
/*     */     case 0:
/*  54 */       System.out.println(logText);
/*  55 */       break;
/*     */     case 1:
/*  57 */       System.out.println("DEBUG: " + logText);
/*     */ 
/*  59 */       break;
/*     */     case 2:
/*  61 */       System.out.println("INFO: " + logText);
/*     */ 
/*  63 */       break;
/*     */     case 3:
/*  65 */       System.out.println("WARN: " + logText);
/*     */ 
/*  67 */       break;
/*     */     case 4:
/*  69 */       System.out.println("ERROR: " + logText);
/*     */ 
/*  71 */       break;
/*     */     case 5:
/*  73 */       System.out.println("current time");
/*     */ 
/*  75 */       break;
/*     */     case 6:
/*  77 */       System.out.println("current date and time");
/*     */ 
/*  79 */       break;
/*     */     case 7:
/*  81 */       System.out.println("current running time");
/*     */ 
/*  83 */       break;
/*     */     default:
/*  85 */       System.out.println(logText);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void logCommon(String logText, Integer logLevel) {
/*  90 */     logCommon(logText, logLevel, this.printLevelDefault);
/*     */   }
/*     */ 
/*     */   public void log(Object logText) {
/*  94 */     if (logText == null) {
/*  95 */       logText = "";
/*     */     }
/*  97 */     if (this.insideScraper.booleanValue())
/*  98 */       logCommon(logText.toString(), this.defaultLogLevel);
/*     */     else
/* 100 */       logCommon(logText.toString(), this.defaultLogLevel);
/*     */   }
/*     */ 
/*     */   public void logDebug(Object logText)
/*     */   {
/* 105 */     if (this.insideScraper.booleanValue())
/* 106 */       logCommon(logText.toString(), Integer.valueOf(1));
/*     */     else
/* 108 */       logCommon("DEBUG: " + logText, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public void logInfo(Object logText)
/*     */   {
/* 113 */     if (this.insideScraper.booleanValue())
/* 114 */       logCommon(logText.toString(), Integer.valueOf(2));
/*     */     else
/* 116 */       logCommon("INFO: " + logText, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public void logWarn(Object logText)
/*     */   {
/* 121 */     if (this.insideScraper.booleanValue())
/* 122 */       logCommon(logText.toString(), Integer.valueOf(3));
/*     */     else
/* 124 */       logCommon("WARN: " + logText, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public void logError(Object logText)
/*     */   {
/* 129 */     if (this.insideScraper.booleanValue())
/* 130 */       logCommon(logText.toString(), Integer.valueOf(4));
/*     */     else
/* 132 */       logCommon("ERROR: " + logText, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   public String recKeysToString(Record rec, String delimiter, String encloser, boolean escapeEncloser)
/*     */   {
/* 138 */     StringBuilder sb = new StringBuilder();
/* 139 */     if (delimiter.equals("")) {
/* 140 */       delimiter = ",";
/*     */     }
/* 142 */     if (encloser.equals("")) {
/* 143 */       encloser = "'";
/*     */     }
/* 145 */     String tempStr = encloser;
/* 146 */     String drString = "";
/*     */ 
/* 148 */     for (Iterator keys = rec.getOrderedKeysIterator(); keys.hasNext(); ) {
/* 149 */       if (escapeEncloser)
/* 150 */         tempStr = keys.next().toString().replace(encloser, 
/* 151 */           "\\" + encloser);
/*     */       else {
/* 153 */         tempStr = keys.next().toString();
/*     */       }
/*     */ 
/* 156 */       sb.append(encloser + tempStr + encloser);
/* 157 */       if (keys.hasNext())
/*     */       {
/* 159 */         sb.append(delimiter);
/*     */       }
/*     */     }
/* 162 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String recValuesToString(Record dr, String delimiter, String encloser, boolean escapeEncloser)
/*     */   {
/* 167 */     StringBuilder sb = new StringBuilder();
/* 168 */     if (delimiter.equals("")) {
/* 169 */       delimiter = ",";
/*     */     }
/* 171 */     if (encloser.equals("")) {
/* 172 */       encloser = "'";
/*     */     }
/* 174 */     String tempStr = encloser;
/* 175 */     String drString = "";
/*     */ 
/* 177 */     for (Iterator keys = dr.getOrderedKeysIterator(); keys.hasNext(); ) {
/* 178 */       if (escapeEncloser)
/* 179 */         tempStr = String.valueOf(dr.get(keys.next())).replace(encloser, 
/* 180 */           "\\" + encloser);
/*     */       else {
/* 182 */         tempStr = dr.get(keys.next()).toString();
/*     */       }
/*     */ 
/* 185 */       sb.append(encloser);
/* 186 */       sb.append(tempStr);
/* 187 */       sb.append(encloser);
/* 188 */       if (keys.hasNext())
/*     */       {
/* 190 */         sb.append(delimiter);
/*     */       }
/*     */     }
/* 193 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public void printDataTable(DataTable ds, int logLevel) {
/* 197 */     if (ds.getNumDataRecords() == 0) {
/* 198 */       logCommon(
/* 199 */         "----------------- printDataSet WARNING: no records founds in: " + 
/* 200 */         ds.toString(), Integer.valueOf(logLevel));
/*     */     } else {
/* 202 */       logCommon(recKeysToString(ds.getDataRecord(0), " , ", "\"", 
/* 203 */         true), Integer.valueOf(logLevel));
/* 204 */       for (int i = 0; i < ds.getNumDataRecords(); i++)
/* 205 */         logCommon(recValuesToString(ds.getDataRecord(i), " , ", 
/* 206 */           "\"", true), Integer.valueOf(logLevel));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void printDataTable(DataTable ds)
/*     */   {
/* 213 */     printDataTable(ds, this.defaultLogLevel.intValue());
/*     */   }
/*     */ 
/*     */   public void printRecord(Record dr, int logLevel)
/*     */   {
/* 218 */     for (Iterator keys = dr.getOrderedKeysIterator(); keys.hasNext(); ) {
/* 219 */       Object key = keys.next();
/* 220 */       logCommon(String.valueOf(key) + ": " + String.valueOf(dr.get(key)), 
/* 221 */         Integer.valueOf(logLevel));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Record cloneRecord(Record dr) {
/* 226 */     Record newDr = new Record();
/*     */ 
/* 228 */     for (Iterator keys = dr.getOrderedKeysIterator(); keys.hasNext(); ) {
/* 229 */       Object key = keys.next();
/* 230 */       newDr.put(key, dr.get(key));
/*     */     }
/* 232 */     return newDr;
/*     */   }
/*     */ 
/*     */   public void printRecord(Record rec) {
/* 236 */     printRecord(rec, this.defaultLogLevel.intValue());
/*     */   }
/*     */ 
/*     */   public Object transformObject(Object obj, DataTransform transform)
/*     */   {
/* 246 */     obj = transform.transform(obj);
/* 247 */     log(obj.toString());
/* 248 */     return obj;
/*     */   }
/*     */ 
/*     */   public static List<DataTable> splitDataSet(DataTable dataSet, Integer maxSize)
/*     */   {
/* 404 */     ArrayList dataSets = new ArrayList();
/* 405 */     int recordCount = 0;
/* 406 */     if (dataSet.size() > maxSize.intValue()) {
/* 407 */       DataTable ds = new DataTable();
/* 408 */       for (int i = 0; i < dataSet.getNumDataRecords(); i++) {
/* 409 */         recordCount++;
/* 410 */         ds.addDataRecord(dataSet.getDataRecord(i));
/* 411 */         if (recordCount >= maxSize.intValue()) {
/* 412 */           dataSets.ensureCapacity(dataSets.size() + 1);
/* 413 */           dataSets.add(ds);
/* 414 */           ds = new DataTable();
/* 415 */           recordCount = 0;
/*     */         }
/*     */       }
/*     */ 
/* 419 */       dataSets.ensureCapacity(dataSets.size() + 1);
/* 420 */       dataSets.add(ds);
/*     */     } else {
/* 422 */       dataSets.add(dataSet);
/*     */     }
/* 424 */     return dataSets;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.ssTools
 * JD-Core Version:    0.6.2
 */