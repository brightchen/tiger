package cg.dimension.model.aggregate;

public abstract class AbstractHistoryValueAggregateSum<T extends AbstractHistoryValueAggregateSum<T, V>, V extends Number> 
    extends AbstractHistoryValueAggregate<T, V>
{
  @Override
  public V getValue()
  {
    rwLock.readLock().lock();
    try
    {
      for(V value : values)
        sum(value);
    }
    finally
    {
      rwLock.readLock().unlock();
    }
    return getSum();
  }

  protected abstract void sum(V value);
  protected abstract V getSum();
}
