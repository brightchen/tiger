package cg.dimension.model.aggregate;

import cg.common.Calculator;

public class IncrementalAggregateSum<T extends Number> implements Aggregate<T>
{
  protected T sum;
  
  public IncrementalAggregateSum(Class<T> type)
  {
    sum = Calculator.setValue(type, 0.0);
  }
  
  @Override
  public T getValue()
  {
    return sum;
  }

  @Override
  public void addValue(T value)
  {
    sum = Calculator.add(sum, value);
  }

}
