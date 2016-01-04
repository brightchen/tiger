package cg.dimension.model.aggregate;

import cg.common.math.Calculator;

public class IncrementalAggregateSum<AV extends Number> implements CloneableAggregate<IncrementalAggregateSum<AV>, AV>
{
  protected AV sum;
  
  public IncrementalAggregateSum(){}
  
  public IncrementalAggregateSum(Class<AV> type)
  {
    initSum(type);
  }
  
  public void initSum(Class<AV> type)
  {
    sum = Calculator.setValue(type, 0.0);
  }
  
  @Override
  public AV getValue()
  {
    return sum;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addValue(AV value)
  {
    if(sum == null)
    {
      //use the type of value as the type of sum
      initSum((Class<AV>)value.getClass());
    }
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
