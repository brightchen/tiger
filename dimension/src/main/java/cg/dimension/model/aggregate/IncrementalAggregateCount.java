package cg.dimension.model.aggregate;

import cg.common.Calculator;

public class IncrementalAggregateCount implements Aggregate<Long>
{
  protected long count = 0;
  
  public IncrementalAggregateCount()
  {
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

}