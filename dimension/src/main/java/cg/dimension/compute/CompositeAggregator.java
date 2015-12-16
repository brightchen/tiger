package cg.dimension.compute;

import java.util.List;

import com.google.common.collect.Lists;

import cg.common.Calculator;

/**
 * This aggregator keep lots of sub aggregators
 * preconditions
 *   - handle same type of record
 *   - the aggregate should be same type: for example, all are SUM
 *   - the type of match value should be same
 * 
 * TimeBucketsAggregator is a typical case.
 * 
 * @author bright
 *
 */
public class CompositeAggregator<B, MV, AV extends Number> implements Aggregator<B, MV, AV>
{
  protected String name;
  protected List<Aggregator<B, MV, AV>> subAggregators = Lists.newArrayList();
  protected Class<AV> valueType;
  
  public CompositeAggregator(Class<AV> valueType)
  {
    this.valueType = valueType;
  }
  
  public CompositeAggregator()
  {
  }
  
  /**
   * the relationship of criteria is or
   */
  @Override
  public boolean matches(MV value)
  {
    for(Aggregator<B, MV, AV> subAggregator : subAggregators)
    {
      if(subAggregator.matches(value))
        return true;
    }
    return false;
  }

  @Override
  public AV getValue()
  {
    //depends on the aggregate type, for sum and count, add all. 
    //implement add here.
    AV value = Calculator.setValue(valueType, 0.0);
    for(Aggregator<B, MV, AV> subAggregator : subAggregators)
    {
      value = Calculator.add(value, subAggregator.getValue());
    }
    return value;
  }

  @Override
  public void addValue(AV value)
  {
    //TODO: should implement this?
//    for(Aggregator<B, MV, AV> subAggregator : subAggregators)
//    {
//      if(subAggregator.matches(value))
//        subAggregator.addValue(value);
//    }
    
  }

  @Override
  public void processMatchedValue(AV value)
  {
    addValue(value);
  }

  @Override
  public void processBean(B bean)
  {
    //let the sub aggregator handle this bean
    for(Aggregator<B, MV, AV> subAggregator : subAggregators)
    {
      subAggregator.processBean(bean);
    }
  }

  public Class<AV> getValueType()
  {
    return valueType;
  }
  public void setValueType(Class<AV> valueType)
  {
    this.valueType = valueType;
  }

  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  
}
