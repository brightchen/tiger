package cg.dimension.model.aggregate;

import cg.common.math.Calculator;

public class IncrementalAggregateSum<AV extends Number> implements CloneableAggregate<AV>
{
  protected AV sum;
  
  public IncrementalAggregateSum(){}
  
  public IncrementalAggregateSum(Class<AV> type)
  {
    sum = Calculator.setValue(type, 0.0);
  }
  
  @Override
  public AV getValue()
  {
    return sum;
  }

  @Override
  public void addValue(AV value)
  {
    sum = Calculator.add(sum, value);
  }

  @Override
  public IncrementalAggregateSum<AV> cloneMe()
  {
    IncrementalAggregateSum<AV> cloned = new IncrementalAggregateSum<>();
    cloned.sum = this.sum;
    return cloned;
  }



}
