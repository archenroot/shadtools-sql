/*     */ package com.shadworld.sql.orm;
/*     */ 
/*     */ import com.shadworld.sql.dbutils.CustomPropertyHandler;
/*     */ import com.shadworld.sql.dbutils.MissingPropertyHandler;
/*     */ import com.shadworld.sql.orm.naming.CamelCaseToUnderscoreNamingStrategy;
/*     */ import com.shadworld.sql.orm.naming.NamingStrategy;
/*     */ import com.shadworld.utils.BeanTools;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.beanutils.PropertyUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class BeanRegistry
/*     */ {
/*  35 */   private static BeanRegistry instance = new BeanRegistry();
/*     */ 
/*  37 */   private HashMap<Class, RegistryEntry> registry = new HashMap();
/*     */ 
/*  39 */   private static HashSet<Class> simpleClasses = new HashSet(Arrays.asList(new Class[] { 
/*  40 */     String.class, Character.class, Byte.class, Boolean.class, Short.class, 
/*  41 */     Integer.class, Long.class, Float.class, Double.class, Date.class }));
/*     */ 
/*  44 */   private String defaultTablePrefix = "";
/*     */ 
/*     */   public static BeanRegistry get() {
/*  47 */     return instance;
/*     */   }
/*     */ 
/*     */   public RegistryEntry getRegistryEntry(Class c)
/*     */   {
/*  57 */     RegistryEntry entry = (RegistryEntry)this.registry.get(c);
/*  58 */     if (entry == null) {
/*  59 */       entry = new RegistryEntry(c);
/*  60 */       this.registry.put(c, entry);
/*     */     }
/*  62 */     return entry;
/*     */   }
/*     */ 
/*     */   public NamingStrategy getNamingStrategy(Class c) {
/*  66 */     RegistryEntry entry = getRegistryEntry(c);
/*  67 */     if (entry.namingStrategy == null)
/*  68 */       entry.namingStrategy = new CamelCaseToUnderscoreNamingStrategy(c);
/*  69 */     return entry.namingStrategy;
/*     */   }
/*     */ 
/*     */   public String getTableName(Class c)
/*     */   {
/*  79 */     RegistryEntry entry = getRegistryEntry(c);
/*  80 */     if (entry.tableName != null)
/*  81 */       return entry.tableName;
/*  82 */     String tableName = JPAUtil.getTableName(c);
/*  83 */     if ((tableName == null) || (tableName.isEmpty())) {
/*  84 */       tableName = getNamingStrategy(c).getTableName(c);
/*     */     }
/*  86 */     if (tableName != null) {
/*  87 */       tableName = this.defaultTablePrefix + tableName;
/*     */     }
/*  89 */     entry.tableName = tableName;
/*  90 */     return tableName;
/*     */   }
/*     */ 
/*     */   public String getBeanPrimaryKey(Object bean)
/*     */   {
/* 102 */     StringBuilder sb = new StringBuilder();
/* 103 */     List props = getIdPropertiesOrDefault(bean.getClass());
/* 104 */     for (Iterator i = props.iterator(); i.hasNext(); ) {
/* 105 */       PropertyDescriptor prop = (PropertyDescriptor)i.next();
/* 106 */       Method getter = prop.getReadMethod();
/* 107 */       Object value = "not_invoked";
/*     */       try {
/* 109 */         value = getter.invoke(bean, new Object[0]);
/*     */       } catch (Exception e) {
/* 111 */         e.printStackTrace();
/*     */       }
/* 113 */       sb.append(value);
/* 114 */       if (i.hasNext())
/* 115 */         sb.append("-");
/*     */     }
/* 117 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public List<PropertyDescriptor> getIdPropertiesOrDefault(Class c)
/*     */   {
/* 126 */     return getIdProperties(c, new String[] { "id" });
/*     */   }
/*     */ 
/*     */   public List<PropertyDescriptor> getIdProperties(Class c, String[] defaultProperties)
/*     */   {
/* 143 */     RegistryEntry entry = getRegistryEntry(c);
/*     */ 
/* 145 */     if (entry.primaryKeyPropertyDescriptors.size() > 0) {
/* 146 */       return entry.primaryKeyPropertyDescriptors;
/*     */     }
/*     */ 
/* 149 */     entry.primaryKeyPropertyDescriptors = JPAUtil.getPrimaryKeyProperties(c, entry.properties);
/* 150 */     if (entry.primaryKeyPropertyDescriptors.size() > 0) {
/* 151 */       entry.primaryKeyPropertyNames.clear();
/* 152 */       for (PropertyDescriptor prop : entry.primaryKeyPropertyDescriptors) {
/* 153 */         entry.primaryKeyPropertyNames.add(prop.getName());
/*     */       }
/* 155 */       return entry.primaryKeyPropertyDescriptors;
/*     */     }
/*     */ 
/* 159 */     List ucs = JPAUtil.getUniqueContraints(c);
/* 160 */     if ((ucs != null) && (ucs.size() > 0)) {
/* 161 */       Object props = new ArrayList();
/* 162 */       List propNames = new ArrayList();
/* 163 */       for (String uc : (String[])ucs.get(0)) {
/* 164 */         String propName = entry.namingStrategy.getColumnPropertyName(uc);
/* 165 */         PropertyDescriptor prop = (PropertyDescriptor)entry.propertyIndex.get(propName);
/* 166 */         if (prop != null) {
/* 167 */           ((List)props).add(prop);
/* 168 */           propNames.add(propName);
/*     */         } else {
/* 170 */           throw new RuntimeException("Could not find matching property for UniqueContraint column: " + uc);
/*     */         }
/*     */       }
/* 173 */       entry.primaryKeyPropertyDescriptors = ((List)props);
/* 174 */       entry.primaryKeyPropertyNames = propNames;
/* 175 */       return entry.primaryKeyPropertyDescriptors;
/*     */     }
/*     */ 
/* 179 */     if ((defaultProperties != null) && (defaultProperties.length > 0)) {
/* 180 */       Object props = new ArrayList();
/* 181 */       List propNames = new ArrayList();
/*     */ 
/* 183 */       for (String propName : defaultProperties) {
/* 184 */         PropertyDescriptor prop = (PropertyDescriptor)entry.propertyIndex.get(propName);
/* 185 */         if (prop != null) {
/* 186 */           ((List)props).add(prop);
/* 187 */           propNames.add(propName);
/*     */         }
/*     */         else {
/* 190 */           throw new RuntimeException("Could not find default property: " + propName);
/*     */         }
/*     */       }
/* 193 */       entry.primaryKeyPropertyDescriptors = ((List)props);
/* 194 */       entry.primaryKeyPropertyNames = propNames;
/* 195 */       return entry.primaryKeyPropertyDescriptors;
/*     */     }
/*     */ 
/* 199 */     throw new RuntimeException(
/* 200 */       "Cannot find an Id property by any method.  See javadoc for description of how id's are search for.  You may try manually registering an ID property with BeanRegistry.registerIdProperties(...)");
/*     */   }
/*     */ 
/*     */   public String getPropertyColumnName(Class c, PropertyDescriptor property)
/*     */   {
/* 211 */     if (property == null)
/* 212 */       throw new IllegalArgumentException("property descriptor cannot be null, if you search by property name the property is missing for class: " + c.getName());
/* 213 */     RegistryEntry entry = getRegistryEntry(c);
/* 214 */     String colName = (String)entry.propertyToColumnNames.get(property.getName());
/*     */ 
/* 216 */     if (colName == null) {
/* 217 */       colName = JPAUtil.getColumnName(c, property);
/*     */ 
/* 219 */       if ((colName == null) || ((colName.isEmpty()) && 
/* 220 */         (!property.getPropertyType().isPrimitive()) && 
/* 221 */         (!simpleClasses.contains(property.getPropertyType())) && 
/* 222 */         (!property.getPropertyType().isEnum()))) {
/* 223 */         Annotation anno = JPAUtil.findJPAMappingClass(c, property);
/* 224 */         if (anno != null) {
/* 225 */           List idProps = JPAUtil.getPrimaryKeyProperties(property.getPropertyType(), PropertyUtils.getPropertyDescriptors(property.getPropertyType()));
/* 226 */           StringBuilder sb = new StringBuilder(property.getName());
/* 227 */           if (idProps.size() == 0)
/* 228 */             sb.append(StringUtils.capitalize("id"));
/* 229 */           for (PropertyDescriptor idProp : idProps) {
/* 230 */             sb.append(StringUtils.capitalize(idProp.getName().toLowerCase()));
/*     */           }
/* 232 */           colName = getNamingStrategy(c).getPropertyColumnName(sb.toString());
/* 233 */           entry.mappedProperties.add(property.getName());
/*     */         }
/*     */       }
/* 236 */       if ((colName == null) || (colName.isEmpty())) {
/* 237 */         colName = getNamingStrategy(c).getPropertyColumnName(property);
/*     */       }
/* 239 */       entry.propertyToColumnNames.put(property.getName(), colName);
/* 240 */       entry.columnToPropertyNames.put(colName, property.getName());
/*     */     }
/* 242 */     return colName;
/*     */   }
/*     */ 
/*     */   public String getPropertyColumnName(Class c, String propName)
/*     */   {
/* 253 */     RegistryEntry entry = getRegistryEntry(c);
/* 254 */     return getPropertyColumnName(c, (PropertyDescriptor)entry.propertyIndex.get(propName));
/*     */   }
/*     */ 
/*     */   public String getColumnPropertyName(Class c, String columnName)
/*     */   {
/* 285 */     RegistryEntry entry = getRegistryEntry(c);
/* 286 */     String propName = (String)entry.columnToPropertyNames.get(columnName);
/* 287 */     if (propName == null) {
/* 288 */       propName = getNamingStrategy(c).getColumnPropertyName(columnName);
/* 289 */       entry.propertyToColumnNames.put(propName, columnName);
/* 290 */       entry.columnToPropertyNames.put(columnName, propName);
/*     */     }
/*     */ 
/* 293 */     return propName;
/*     */   }
/*     */ 
/*     */   public void registerMissingPropertyHandler(Class c, MissingPropertyHandler handler)
/*     */   {
/* 304 */     getRegistryEntry(c).missingPropertyHandlers.add(handler);
/*     */   }
/*     */ 
/*     */   public Set<MissingPropertyHandler> getMissingPropertyHandlers(Class c) {
/* 308 */     return getRegistryEntry(c).missingPropertyHandlers;
/*     */   }
/*     */ 
/*     */   public Map<String, CustomPropertyHandler> getCustomPropertyHandlers(Class c) {
/* 312 */     HashMap map = getRegistryEntry(c).customPropertyHandlers;
/* 313 */     if (map == null)
/* 314 */       map = new HashMap();
/* 315 */     return map;
/*     */   }
/*     */ 
/*     */   public void registerCustomPropertyHandler(Class c, String propertyName, CustomPropertyHandler handler)
/*     */   {
/* 326 */     HashMap customMap = getRegistryEntry(c).customPropertyHandlers;
/* 327 */     if (customMap == null) {
/* 328 */       customMap = new HashMap();
/* 329 */       getRegistryEntry(c).customPropertyHandlers = customMap;
/*     */     }
/* 331 */     customMap.put(propertyName, handler);
/*     */   }
/*     */ 
/*     */   public void registerTableName(Class c, String tableName) {
/* 335 */     getRegistryEntry(c).tableName = tableName;
/*     */   }
/*     */ 
/*     */   public void registerIdProperties(Class c, String[] propertyNames)
/*     */   {
/* 345 */     RegistryEntry entry = getRegistryEntry(c);
/* 346 */     entry.primaryKeyPropertyNames = new ArrayList(Arrays.asList(propertyNames));
/* 347 */     entry.primaryKeyPropertyDescriptors.clear();
/* 348 */     for (String propName : propertyNames) {
/* 349 */       PropertyDescriptor prop = (PropertyDescriptor)entry.propertyIndex.get(propName);
/* 350 */       if (prop == null)
/* 351 */         throw new IllegalArgumentException("Cannot index ID property: " + propName + ". Property not found");
/* 352 */       entry.primaryKeyPropertyDescriptors.add(prop);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PropertyDescriptor getPropertyDescriptor(Class type, String propName) {
/* 357 */     return (PropertyDescriptor)getRegistryEntry(type).propertyIndex.get(propName); } 
/* 361 */   public class RegistryEntry { HashSet<String> mappedProperties = new HashSet();
/*     */     PropertyDescriptor[] properties;
/* 363 */     HashMap<String, PropertyDescriptor> propertyIndex = new HashMap();
/*     */     NamingStrategy namingStrategy;
/*     */     String tableName;
/* 366 */     List<String> primaryKeyPropertyNames = new ArrayList();
/* 367 */     List<PropertyDescriptor> primaryKeyPropertyDescriptors = new ArrayList();
/*     */ 
/* 369 */     HashMap<String, String> propertyToColumnNames = new HashMap();
/* 370 */     HashMap<String, String> columnToPropertyNames = new HashMap();
/* 371 */     HashSet<MissingPropertyHandler> missingPropertyHandlers = new HashSet();
/*     */     HashMap<String, CustomPropertyHandler> customPropertyHandlers;
/*     */ 
/* 375 */     protected RegistryEntry(Class c) { if (c != null) {
/* 376 */         this.properties = BeanTools.propertyDescriptors(c);
/* 377 */         for (PropertyDescriptor prop : this.properties)
/* 378 */           this.propertyIndex.put(prop.getName(), prop);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void setPrimaryKeyProperties(List<PropertyDescriptor> props)
/*     */     {
/* 384 */       this.primaryKeyPropertyDescriptors = props;
/* 385 */       this.primaryKeyPropertyNames.clear();
/* 386 */       for (PropertyDescriptor prop : this.primaryKeyPropertyDescriptors)
/* 387 */         this.primaryKeyPropertyNames.add(prop.getName());
/*     */     }
/*     */ 
/*     */     public void setPrimaryKeyPropertiesByName(List<String> props)
/*     */     {
/* 392 */       this.primaryKeyPropertyNames = props;
/* 393 */       this.primaryKeyPropertyDescriptors.clear();
/* 394 */       for (String propName : this.primaryKeyPropertyNames) {
/* 395 */         PropertyDescriptor prop = (PropertyDescriptor)this.propertyIndex.get(propName);
/* 396 */         if (prop == null) {
/* 397 */           throw new RuntimeException("Tried to add property name: " + propName + 
/* 398 */             " to primary key index but property does not exist");
/*     */         }
/* 400 */         this.primaryKeyPropertyDescriptors.add(prop);
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean isMappedProperty(PropertyDescriptor property)
/*     */     {
/* 410 */       return this.mappedProperties.contains(property.getName());
/*     */     }
/*     */ 
/*     */     public boolean isMappedProperty(String propName)
/*     */     {
/* 418 */       return this.mappedProperties.contains(propName);
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.orm.BeanRegistry
 * JD-Core Version:    0.6.2
 */