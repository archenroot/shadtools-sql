package com.shadworld.sql.query.column;

public abstract interface IValueModel<C>
{
  public abstract C getValue(int paramInt1, int paramInt2, Object paramObject);

  public abstract C getValue(Object paramObject);

  public abstract C getValue(int paramInt, Object paramObject);

  public abstract boolean isLastValueLiteral();
}

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-sql-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.sql.query.column.IValueModel
 * JD-Core Version:    0.6.2
 */