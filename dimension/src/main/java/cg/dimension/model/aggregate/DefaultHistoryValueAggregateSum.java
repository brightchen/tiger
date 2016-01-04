package cg.dimension.model.aggregate;

import cg.common.math.Calculator;

public class DefaultHistoryValueAggregateSum<V extends Number> 
    extends AbstractHistoryValueAggregateSum<DefaultHistoryValueAggregateSum<V>, V>
{
  protected V sum;
  
  public DefaultHistoryValueAggregateSum(){}
  
  public DefaultHistoryValueAggregateSum(Class<V> type)
  {
    sum = Calculator.setValue(type, 0.0);
  }
  
  
  @Override
  public DefaultHistoryValueAggregateSum<V> cloneMe()
  {
    DefaultHistoryValueAggregateSum<V> cloned = new DefaultHistoryValueAggregateSum<>();
    cloned.sum = this.sum;
    return cloned;
  }

  @Override
  protected void sum(V value)
  {
    sum = Calculator.add(sum, value);
  }

  @Override
  protected V getSum()
  {
    return sum;
  }
}
