package cg.dimension.model.aggregate;

import cg.common.Calculator;

public class IncrementalAggregateCount implements Aggregate<Long>
{
  protected long count = 0;
  
  public IncrementalAggregateCount()
  {
  }
  
  public IncrementalAggregateCount(long count)
  {
    this.count = count;
  }
  
  @Override
  public Long getValue()
  {
    return count;
  }

  /**
   * for Count, ignore the value of bean
   */
  @Override
  public void addValue(Long value)
  {
    count++;
  }

  @Override
  public IncrementalAggregateCount cloneMe()
  {
    return new IncrementalAggregateCount(count);
  }

}