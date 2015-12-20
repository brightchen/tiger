package cg.dimension.model.aggregate;

import cg.common.math.Calculator;

public class DefaultHistoryValueAggregateSum<T extends Number> extends AbstractHistoryValueAggregateSum<T>
{
  protected T sum;
  
  public DefaultHistoryValueAggregateSum(){}
  
  public DefaultHistoryValueAggregateSum(Class<T> type)
  {
    sum = Calculator.setValue(type, 0.0);
  }
  
  
  @Override
  public DefaultHistoryValueAggregateSum<T> cloneMe()
  {
    DefaultHistoryValueAggregateSum<T> cloned = new DefaultHistoryValueAggregateSum<>();
    cloned.sum = this.sum;
    return cloned;
  }

  @Override
  protected void sum(T value)
  {
    sum = Calculator.add(sum, value);
  }

  @Override
  protected T getSum()
  {
    return sum;
  }

}
