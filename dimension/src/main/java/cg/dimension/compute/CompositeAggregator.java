package cg.dimension.compute;

import java.util.List;

import com.google.common.collect.Lists;

import cg.common.Calculator;

/**
 * This aggregator keep lots of sub aggregators
 * preconditions
 *   - the aggregate should be same type: for example, all are SUM
 *   - handle same type of record
 *   - 
 * 
 * @author bright
 *
 */
public class CompositeAggregator<B, V extends Number> implements Aggregator<B, V>
{
  protected List<Aggregator<B, V>> subAggregators = Lists.newArrayList();
  
  protected Class<V> valueType;
  
  public CompositeAggregator(Class<V> valueType)
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
  public boolean matches(V value)
  {
    for(Aggregator<B, V> subAggregator : subAggregators)
    {
      if(subAggregator.matches(value))
        return true;
    }
    return false;
  }

  @Override
  public V getValue()
  {
    //depends on the aggregate type, for sum and count, add all. 
    //implement add here.
    V value = Calculator.setValue(valueType, 0.0);
    for(Aggregator<B, V> subAggregator : subAggregators)
    {
      value = Calculator.add(value, subAggregator.getValue());
    }
    return value;
  }

  @Override
  public void addValue(V value)
  {
    for(Aggregator<B, V> subAggregator : subAggregators)
    {
      if(subAggregator.matches(value))
        subAggregator.addValue(value);
    }
    
  }

  @Override
  public void processMatchedValue(V value)
  {
    addValue(value);
  }

  @Override
  public void processBean(B bean)
  {
    //let the sub aggregator handle this bean
    for(Aggregator<B, V> subAggregator : subAggregators)
    {
      subAggregator.processBean(bean);
    }
  }

  public Class<V> getValueType()
  {
    return valueType;
  }

  public void setValueType(Class<V> valueType)
  {
    this.valueType = valueType;
  }

}
