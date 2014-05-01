/*     */ package com.shadworld.sql.dbutils;
/*     */ 
/*     */ import com.shadworld.sql.SqlUtil;
/*     */ import com.shadworld.sql.orm.BeanIndex;
/*     */ import com.shadworld.sql.orm.BeanRegistry;
/*     */ import com.shadworld.sql.orm.BeanRegistry.RegistryEntry;
/*     */ import com.shadworld.sql.orm.naming.NamingStrategy;
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import com.shadworld.utils.Convert;
/*     */ import com.shadworld.utils.StringTools;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.dbutils.BeanProcessor;
/*     */ 
/*     */ public class ShadBeanProcessor extends BeanProcessor
/*     */ {
/*     */   protected static final int PROPERTY_NOT_FOUND = -1;
/*     */   protected static final int PROPERTY_NOT_FOR_THS_BEAN = -2;
/*     */   protected static final int PROPERTY_MAPPED_TO_BEAN = -3;
/*  49 */   private static final Map<Class<?>, Object> primitiveDefaults = new HashMap();
/*     */ 
/*  62 */   private HashMap<Object, Record> missedProperties = new HashMap();
/*  63 */   private HashMap<Class, int[]> colPropMap = new HashMap();
/*  64 */   private SaveMissingPropertyHandler saveMissingPropertyHandler = new SaveMissingPropertyHandler(false);
/*  65 */   private BeanIndex beanIndex = new BeanIndex();
/*     */ 
/*     */   static
/*     */   {
/*  52 */     primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
/*  53 */     primitiveDefaults.put(Short.TYPE, Short.valueOf((short)0));
/*  54 */     primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte)0));
/*  55 */     primitiveDefaults.put(Float.TYPE, Float.valueOf(0.0F));
/*  56 */     primitiveDefaults.put(Double.TYPE, Double.valueOf(0.0D));
/*  57 */     primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
/*  58 */     primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
/*  59 */     primitiveDefaults.put(Character.TYPE, Character.valueOf('\000'));
/*     */   }
/*     */ 
/*     */   protected Object processColumn(ResultSet rs, int index, Class<?> propType)
/*     */     throws SQLException
/*     */   {
/*  70 */     return super.processColumn(rs, index, propType);
/*     */   }
/*     */ 
/*     */   public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException
/*     */   {
/*  75 */     if (!rs.next())
/*  76 */       return null;
/*  77 */     Object bean = newInstance(type);
/*  78 */     return toThisBean(rs, bean);
/*     */   }
/*     */ 
/*     */   public <T> T toThisBean(ResultSet rs, T bean)
/*     */     throws SQLException
/*     */   {
/*  90 */     Class type = bean.getClass();
/*     */ 
/*  92 */     PropertyDescriptor[] props = propertyDescriptors(type);
/*     */ 
/*  94 */     ResultSetMetaData rsmd = rs.getMetaData();
/*  95 */     int[] columnToProperty = mapColumnsToProperties(rsmd, props, type);
/*     */ 
/*  97 */     if (rs.isBeforeFirst()) {
/*  98 */       rs.next();
/*     */     }
/* 100 */     return fillBean(rs, bean, props, columnToProperty);
/*     */   }
/*     */ 
/*     */   public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException
/*     */   {
/* 105 */     List results = new ArrayList();
/*     */ 
/* 107 */     if (!rs.next()) {
/* 108 */       return results;
/*     */     }
/*     */ 
/* 111 */     PropertyDescriptor[] props = propertyDescriptors(type);
/* 112 */     ResultSetMetaData rsmd = rs.getMetaData();
/*     */ 
/* 114 */     int[] columnToProperty = mapColumnsToProperties(rsmd, props, type);
/*     */     do
/*     */     {
/* 117 */       Object bean = newInstance(type);
/* 118 */       results.add(fillBean(rs, bean, props, columnToProperty));
/*     */     }
/* 116 */     while (
/* 119 */       rs.next());
/*     */ 
/* 121 */     return results;
/*     */   }
/*     */ 
/*     */   public Object[] toBeans(ResultSet rs, Class[] types)
/*     */     throws SQLException
/*     */   {
/* 132 */     Object[] beans = new Object[types.length];
/* 133 */     for (int i = 0; i < beans.length; i++) {
/* 134 */       rs.beforeFirst();
/* 135 */       Object bean = toBean(rs, types[i]);
/* 136 */       beans[i] = bean;
/*     */     }
/*     */ 
/* 139 */     return beans;
/*     */   }
/*     */ 
/*     */   public Object[] toTheseBeans(ResultSet rs, Object[] beans)
/*     */     throws SQLException
/*     */   {
/* 151 */     for (int i = 0; i < beans.length; i++) {
/* 152 */       rs.beforeFirst();
/* 153 */       beans[i] = toThisBean(rs, beans[i]);
/*     */     }
/*     */ 
/* 156 */     return beans;
/*     */   }
/*     */ 
/*     */   public List<Object>[] toBeanLists(ResultSet rs, Class[] types)
/*     */     throws SQLException
/*     */   {
/* 168 */     BeanIndex index = new BeanIndex();
/* 169 */     List[] beans = new List[types.length];
/* 170 */     for (int i = 0; i < beans.length; i++) {
/* 171 */       rs.beforeFirst();
/* 172 */       beans[i] = toBeanList(rs, types[i]);
/*     */ 
/* 174 */       for (int j = 0; j < beans[i].size(); j++) {
/* 175 */         beans[i].set(j, index.indexBean(beans[i].get(j)));
/*     */       }
/*     */     }
/*     */ 
/* 179 */     return beans;
/*     */   }
/*     */ 
/*     */   public List<Object[]> toBeansList(ResultSet rs, Class[] types)
/*     */     throws SQLException
/*     */   {
/* 191 */     List[] lists = toBeanLists(rs, types);
/* 192 */     List list = new ArrayList();
/* 193 */     for (int i = 0; i < lists[0].size(); i++) {
/* 194 */       Object[] objs = new Object[lists.length];
/* 195 */       for (int j = 0; j < lists.length; j++) {
/* 196 */         objs[j] = lists[j].get(i);
/*     */       }
/* 198 */       list.add(objs);
/*     */     }
/* 200 */     return list;
/*     */   }
/*     */ 
/*     */   public <T> T toBean(ResultSet rs, T bean)
/*     */     throws SQLException
/*     */   {
/* 240 */     Class type = bean.getClass();
/*     */ 
/* 242 */     PropertyDescriptor[] props = propertyDescriptors(type);
/*     */ 
/* 244 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 245 */     int[] columnToProperty = mapColumnsToProperties(rsmd, props);
/*     */ 
/* 247 */     return fillBean(rs, bean, props, columnToProperty);
/*     */   }
/*     */ 
/*     */   public Record toTypedRecord(ResultSet rs) throws SQLException {
/* 251 */     Record rec = new Record();
/* 252 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 253 */     int cols = rsmd.getColumnCount();
/*     */ 
/* 255 */     for (int i = 1; i <= cols; i++) {
/* 256 */       rec.put(SqlUtil.getColumnName(rsmd, i), rs.getObject(i));
/*     */     }
/*     */ 
/* 259 */     return rec;
/*     */   }
/*     */ 
/*     */   public DataTable toTypedDataTable(ResultSet rs) throws SQLException {
/* 263 */     DataTable tbl = new DataTable();
/* 264 */     if (!rs.isBeforeFirst())
/* 265 */       rs.beforeFirst();
/* 266 */     while (rs.next()) {
/* 267 */       tbl.add(toTypedRecord(rs));
/*     */     }
/* 269 */     return tbl;
/*     */   }
/*     */ 
/*     */   public DataTable toStringDataTable(ResultSet rs) throws SQLException {
/* 273 */     DataTable tbl = new DataTable();
/* 274 */     if (!rs.isBeforeFirst())
/* 275 */       rs.beforeFirst();
/* 276 */     while (rs.next()) {
/* 277 */       tbl.add(toStringRecord(rs));
/*     */     }
/* 279 */     return tbl;
/*     */   }
/*     */ 
/*     */   public String toSingleValueString(ResultSet rs) throws SQLException {
/* 283 */     if (rs.next())
/* 284 */       return rs.getString(0);
/* 285 */     return null;
/*     */   }
/*     */ 
/*     */   public Object toSingleValueObject(ResultSet rs) throws SQLException {
/* 289 */     if (rs.next())
/* 290 */       return rs.getObject(0);
/* 291 */     return null;
/*     */   }
/*     */ 
/*     */   public Record toStringRecord(ResultSet rs) throws SQLException {
/* 295 */     Record rec = new Record();
/* 296 */     ResultSetMetaData rsmd = rs.getMetaData();
/* 297 */     int cols = rsmd.getColumnCount();
/*     */ 
/* 299 */     for (int i = 1; i <= cols; i++) {
/* 300 */       rec.put(SqlUtil.getColumnName(rsmd, i), rs.getString(i));
/*     */     }
/* 302 */     return rec;
/*     */   }
/*     */ 
/*     */   protected int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props, Class type)
/*     */     throws SQLException
/*     */   {
/* 326 */     int[] columnToProperty = (int[])this.colPropMap.get(type);
/* 327 */     if (columnToProperty != null) {
/* 328 */       return columnToProperty;
/*     */     }
/*     */ 
/* 331 */     NamingStrategy namer = BeanRegistry.get().getNamingStrategy(type);
/*     */ 
/* 333 */     int cols = rsmd.getColumnCount();
/* 334 */     columnToProperty = new int[cols + 1];
/* 335 */     Arrays.fill(columnToProperty, -1);
/*     */ 
/* 341 */     for (int col = 1; col <= cols; col++)
/*     */     {
/* 343 */       String columnName = rsmd.getColumnLabel(col);
/* 344 */       if ((columnName == null) || (columnName.length() == 0)) {
/* 345 */         columnName = rsmd.getColumnName(col);
/*     */       }
/* 347 */       String columnTableName = rsmd.getTableName(col);
/*     */ 
/* 349 */       if (namer.isTableMatch(columnTableName, type)) {
/* 350 */         for (int i = 0; i < props.length; i++)
/*     */         {
/* 352 */           if (namer.isColumnMatch(columnName, props[i])) {
/* 353 */             BeanRegistry.get().getPropertyColumnName(type, props[i]);
/* 354 */             if (BeanRegistry.get().getRegistryEntry(type).isMappedProperty(props[i])) {
/* 355 */               columnToProperty[col] = -3; break;
/*     */             }
/* 357 */             columnToProperty[col] = i;
/* 358 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 364 */     return columnToProperty;
/*     */   }
/*     */ 
/*     */   private <T> T fillBean(ResultSet rs, T bean, PropertyDescriptor[] props, int[] columnToProperty)
/*     */     throws SQLException
/*     */   {
/* 396 */     Class type = bean.getClass();
/* 397 */     Map customHandlers = BeanRegistry.get().getCustomPropertyHandlers(type);
/*     */ 
/* 399 */     ResultSetMetaData rsmd = rs.getMetaData();
/*     */ 
/* 402 */     NamingStrategy namer = BeanRegistry.get().getNamingStrategy(type);
/*     */ 
/* 404 */     for (int i = 1; i < columnToProperty.length; i++)
/*     */     {
/* 406 */       if (columnToProperty[i] == -1) {
/* 407 */         if (namer.isTableMatch(rsmd.getTableName(i), type)) {
/* 408 */           Set handlers = BeanRegistry.get().getMissingPropertyHandlers(type);
/* 409 */           for (MissingPropertyHandler handler : handlers) {
/* 410 */             if (handler != null) {
/* 411 */               String colName = rsmd.getColumnName(i);
/* 412 */               String colLabel = rsmd.getColumnLabel(i);
/* 413 */               Object value = rs.getObject(i);
/* 414 */               value = handler.handleMissingProperty(i, rs, bean, colName, colLabel, value);
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/* 421 */       else if (columnToProperty[i] != -3)
/*     */       {
/* 425 */         PropertyDescriptor prop = props[columnToProperty[i]];
/* 426 */         Class propType = prop.getPropertyType();
/*     */ 
/* 428 */         Object value = processColumn(rs, i, propType);
/*     */ 
/* 430 */         if ((propType != null) && (value == null) && (propType.isPrimitive())) {
/* 431 */           value = primitiveDefaults.get(propType);
/*     */         }
/*     */ 
/* 434 */         CustomPropertyHandler handler = (CustomPropertyHandler)customHandlers.get(prop.getName());
/* 435 */         if (handler != null) {
/* 436 */           value = handler.handleCustomProperty(i, rs, rsmd, bean, value);
/* 437 */           if (handler.callSetter)
/* 438 */             callSetter(bean, prop, value);
/*     */         }
/* 440 */         else if ((Collection.class.isAssignableFrom(prop.getPropertyType())) && ((value instanceof String))) {
/* 441 */           if ((value != null) && (!value.toString().trim().isEmpty())) {
/*     */             try {
/* 443 */               Collection col = (Collection)prop.getReadMethod().invoke(bean, new Object[0]);
/* 444 */               if (col == null) continue;
/* 445 */               col.addAll(StringTools.split(String.valueOf(value), "\\|", true, true));
/*     */             }
/*     */             catch (Exception e) {
/* 448 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 454 */           callSetter(bean, prop, value);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 459 */     return bean;
/*     */   }
/*     */ 
/*     */   public void callSetter(Object target, PropertyDescriptor prop, Object value)
/*     */     throws SQLException
/*     */   {
/* 477 */     Method setter = prop.getWriteMethod();
/*     */ 
/* 479 */     if (setter == null) {
/* 480 */       return;
/*     */     }
/*     */ 
/* 483 */     Class[] params = setter.getParameterTypes();
/*     */     try
/*     */     {
/* 486 */       if (value != null) {
/* 487 */         if ((value instanceof java.util.Date)) {
/* 488 */           if (params[0].getName().equals("java.sql.Date"))
/* 489 */             value = new java.sql.Date(((java.util.Date)value).getTime());
/* 490 */           else if (params[0].getName().equals("java.sql.Time"))
/* 491 */             value = new Time(((java.util.Date)value).getTime());
/* 492 */           else if (params[0].getName().equals("java.sql.Timestamp")) {
/* 493 */             value = new Timestamp(((java.util.Date)value).getTime());
/*     */           }
/*     */         }
/* 496 */         if ((Boolean.class.isInstance(prop.getPropertyType())) || (Boolean.TYPE.isInstance(prop.getPropertyType()))) {
/* 497 */           value = Convert.toBoolean(value);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 502 */       if (isCompatibleType(value, params[0])) {
/* 503 */         setter.invoke(target, new Object[] { value });
/* 504 */       } else if (Enum.class.isAssignableFrom(params[0])) {
/* 505 */         Class c = params[0];
/* 506 */         setter.invoke(target, new Object[] { Enum.valueOf(c, String.valueOf(value)) });
/*     */       } else {
/* 508 */         throw new SQLException("Cannot set " + prop.getName() + ": incompatible types.");
/*     */       }
/*     */     }
/*     */     catch (IllegalArgumentException e) {
/* 512 */       throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 515 */       throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
/*     */     }
/*     */     catch (InvocationTargetException e) {
/* 518 */       throw new SQLException("Cannot set " + prop.getName() + ": " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isCompatibleType(Object value, Class<?> type)
/*     */   {
/* 537 */     if ((value == null) || (type.isInstance(value))) {
/* 538 */       return true;
/*     */     }
/* 540 */     if ((type.equals(Integer.TYPE)) && (Integer.class.isInstance(value))) {
/* 541 */       return true;
/*     */     }
/* 543 */     if ((type.equals(Long.TYPE)) && (Long.class.isInstance(value))) {
/* 544 */       return true;
/*     */     }
/* 546 */     if ((type.equals(Double.TYPE)) && (Double.class.isInstance(value))) {
/* 547 */       return true;
/*     */     }
/* 549 */     if ((type.equals(Float.TYPE)) && (Float.class.isInstance(value))) {
/* 550 */       return true;
/*     */     }
/* 552 */     if ((type.equals(Short.TYPE)) && (Short.class.isInstance(value))) {
/* 553 */       return true;
/*     */     }
/* 555 */     if ((type.equals(Byte.TYPE)) && (Byte.class.isInstance(value))) {
/* 556 */       return true;
/*     */     }
/* 558 */     if ((type.equals(Character.TYPE)) && (Character.class.isInstance(value))) {
/* 559 */       return true;
/*     */     }
/* 561 */     if ((type.equals(Boolean.TYPE)) && (Boolean.class.isInstance(value))) {
/* 562 */       return true;
/*     */     }
/*     */ 
/* 565 */     return false;
/*     */   }
/*     */ 
/*     */   private PropertyDescriptor[] propertyDescriptors(Class<?> c)
/*     */     throws SQLException
/*     */   {
/* 581 */     BeanInfo beanInfo = null;
/*     */     try {
/* 583 */       beanInfo = Introspector.getBeanInfo(c);
/*     */     }
/*     */     catch (IntrospectionException e) {
/* 586 */       throw new SQLException("Bean introspection failed: " + e.getMessage());
/*     */     }
/*     */ 
/* 589 */     return beanInfo.getPropertyDescriptors();
/*     */   }
/*     */ 
/*     */   public HashMap<Object, Record> getMissedProperties()
/*     */   {
/* 598 */     return this.missedProperties;
/*     */   }
/*     */ 
/*     */   public class SaveMissingPropertyHandler extends MissingPropertyHandler
/*     */   {
/*     */     boolean includeTableInKey;
/*     */ 
/*     */     public SaveMissingPropertyHandler(boolean includeTableInKey)
/*     */     {
/* 614 */       this.includeTableInKey = includeTableInKey;
/*     */     }
/*     */ 
/*     */     protected <T> Object handleMissingProperty(int colNumber, ResultSet rs, T bean, String colName, String colLabel, Object value)
/*     */     {
/* 620 */       Record rec = (Record)ShadBeanProcessor.this.missedProperties.get(bean);
/* 621 */       if (rec == null) {
/* 622 */         rec = new Record();
/* 623 */         ShadBeanProcessor.this.missedProperties.put(bean, rec);
/*     */       }
/* 625 */       String key = "";
/* 626 */       if (this.includeTableInKey) {
/*     */         try {
/* 628 */           ResultSetMetaData rsmd = rs.getMetaData();
/* 629 */           key = rsmd.getTableName(colNumber) + ".";
/*     */         }
/*     */         catch (SQLException e) {
/* 632 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 635 */       rec.put(key + colLabel, value);
/* 636 */       return null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.dbutils.ShadBeanProcessor
 * JD-Core Version:    0.6.2
 */