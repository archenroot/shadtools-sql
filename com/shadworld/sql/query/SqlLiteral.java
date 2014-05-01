/*     */ package com.shadworld.sql.query;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class SqlLiteral
/*     */   implements CharSequence
/*     */ {
/*     */   private String string;
/*     */ 
/*     */   public SqlLiteral(String string)
/*     */   {
/*  25 */     this.string = string;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/*  33 */     return this.string.length();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  41 */     return this.string.isEmpty();
/*     */   }
/*     */ 
/*     */   public char charAt(int index)
/*     */   {
/*  50 */     return this.string.charAt(index);
/*     */   }
/*     */ 
/*     */   public int codePointAt(int index)
/*     */   {
/*  59 */     return this.string.codePointAt(index);
/*     */   }
/*     */ 
/*     */   public int codePointBefore(int index)
/*     */   {
/*  68 */     return this.string.codePointBefore(index);
/*     */   }
/*     */ 
/*     */   public int codePointCount(int beginIndex, int endIndex)
/*     */   {
/*  78 */     return this.string.codePointCount(beginIndex, endIndex);
/*     */   }
/*     */ 
/*     */   public int offsetByCodePoints(int index, int codePointOffset)
/*     */   {
/*  88 */     return this.string.offsetByCodePoints(index, codePointOffset);
/*     */   }
/*     */ 
/*     */   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
/*     */   {
/*  99 */     this.string.getChars(srcBegin, srcEnd, dst, dstBegin);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin)
/*     */   {
/* 111 */     this.string.getBytes(srcBegin, srcEnd, dst, dstBegin);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(String charsetName)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 121 */     return this.string.getBytes(charsetName);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(Charset charset)
/*     */   {
/* 130 */     return this.string.getBytes(charset);
/*     */   }
/*     */ 
/*     */   public byte[] getBytes()
/*     */   {
/* 138 */     return this.string.getBytes();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object anObject)
/*     */   {
/* 147 */     return this.string.equals(anObject);
/*     */   }
/*     */ 
/*     */   public boolean contentEquals(StringBuffer sb)
/*     */   {
/* 156 */     return this.string.contentEquals(sb);
/*     */   }
/*     */ 
/*     */   public boolean contentEquals(CharSequence cs)
/*     */   {
/* 165 */     return this.string.contentEquals(cs);
/*     */   }
/*     */ 
/*     */   public boolean equalsIgnoreCase(String anotherString)
/*     */   {
/* 174 */     return this.string.equalsIgnoreCase(anotherString);
/*     */   }
/*     */ 
/*     */   public int compareTo(String anotherString)
/*     */   {
/* 183 */     return this.string.compareTo(anotherString);
/*     */   }
/*     */ 
/*     */   public int compareToIgnoreCase(String str)
/*     */   {
/* 192 */     return this.string.compareToIgnoreCase(str);
/*     */   }
/*     */ 
/*     */   public boolean regionMatches(int toffset, String other, int ooffset, int len)
/*     */   {
/* 204 */     return this.string.regionMatches(toffset, other, ooffset, len);
/*     */   }
/*     */ 
/*     */   public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)
/*     */   {
/* 217 */     return this.string.regionMatches(ignoreCase, toffset, other, ooffset, len);
/*     */   }
/*     */ 
/*     */   public boolean startsWith(String prefix, int toffset)
/*     */   {
/* 227 */     return this.string.startsWith(prefix, toffset);
/*     */   }
/*     */ 
/*     */   public boolean startsWith(String prefix)
/*     */   {
/* 236 */     return this.string.startsWith(prefix);
/*     */   }
/*     */ 
/*     */   public boolean endsWith(String suffix)
/*     */   {
/* 245 */     return this.string.endsWith(suffix);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 253 */     return this.string.hashCode();
/*     */   }
/*     */ 
/*     */   public int indexOf(int ch)
/*     */   {
/* 262 */     return this.string.indexOf(ch);
/*     */   }
/*     */ 
/*     */   public int indexOf(int ch, int fromIndex)
/*     */   {
/* 272 */     return this.string.indexOf(ch, fromIndex);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(int ch)
/*     */   {
/* 281 */     return this.string.lastIndexOf(ch);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(int ch, int fromIndex)
/*     */   {
/* 291 */     return this.string.lastIndexOf(ch, fromIndex);
/*     */   }
/*     */ 
/*     */   public int indexOf(String str)
/*     */   {
/* 300 */     return this.string.indexOf(str);
/*     */   }
/*     */ 
/*     */   public int indexOf(String str, int fromIndex)
/*     */   {
/* 310 */     return this.string.indexOf(str, fromIndex);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(String str)
/*     */   {
/* 319 */     return this.string.lastIndexOf(str);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(String str, int fromIndex)
/*     */   {
/* 329 */     return this.string.lastIndexOf(str, fromIndex);
/*     */   }
/*     */ 
/*     */   public String substring(int beginIndex)
/*     */   {
/* 338 */     return this.string.substring(beginIndex);
/*     */   }
/*     */ 
/*     */   public String substring(int beginIndex, int endIndex)
/*     */   {
/* 348 */     return this.string.substring(beginIndex, endIndex);
/*     */   }
/*     */ 
/*     */   public CharSequence subSequence(int beginIndex, int endIndex)
/*     */   {
/* 358 */     return this.string.subSequence(beginIndex, endIndex);
/*     */   }
/*     */ 
/*     */   public String concat(String str)
/*     */   {
/* 367 */     return this.string.concat(str);
/*     */   }
/*     */ 
/*     */   public String replace(char oldChar, char newChar)
/*     */   {
/* 377 */     return this.string.replace(oldChar, newChar);
/*     */   }
/*     */ 
/*     */   public boolean matches(String regex)
/*     */   {
/* 386 */     return this.string.matches(regex);
/*     */   }
/*     */ 
/*     */   public boolean contains(CharSequence s)
/*     */   {
/* 395 */     return this.string.contains(s);
/*     */   }
/*     */ 
/*     */   public String replaceFirst(String regex, String replacement)
/*     */   {
/* 405 */     return this.string.replaceFirst(regex, replacement);
/*     */   }
/*     */ 
/*     */   public String replaceAll(String regex, String replacement)
/*     */   {
/* 415 */     return this.string.replaceAll(regex, replacement);
/*     */   }
/*     */ 
/*     */   public String replace(CharSequence target, CharSequence replacement)
/*     */   {
/* 425 */     return this.string.replace(target, replacement);
/*     */   }
/*     */ 
/*     */   public String[] split(String regex, int limit)
/*     */   {
/* 435 */     return this.string.split(regex, limit);
/*     */   }
/*     */ 
/*     */   public String[] split(String regex)
/*     */   {
/* 444 */     return this.string.split(regex);
/*     */   }
/*     */ 
/*     */   public String toLowerCase(Locale locale)
/*     */   {
/* 453 */     return this.string.toLowerCase(locale);
/*     */   }
/*     */ 
/*     */   public String toLowerCase()
/*     */   {
/* 461 */     return this.string.toLowerCase();
/*     */   }
/*     */ 
/*     */   public String toUpperCase(Locale locale)
/*     */   {
/* 470 */     return this.string.toUpperCase(locale);
/*     */   }
/*     */ 
/*     */   public String toUpperCase()
/*     */   {
/* 478 */     return this.string.toUpperCase();
/*     */   }
/*     */ 
/*     */   public String trim()
/*     */   {
/* 486 */     return this.string.trim();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 494 */     return this.string.toString();
/*     */   }
/*     */ 
/*     */   public char[] toCharArray()
/*     */   {
/* 502 */     return this.string.toCharArray();
/*     */   }
/*     */ 
/*     */   public String intern()
/*     */   {
/* 510 */     return this.string.intern();
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.SqlLiteral
 * JD-Core Version:    0.6.2
 */