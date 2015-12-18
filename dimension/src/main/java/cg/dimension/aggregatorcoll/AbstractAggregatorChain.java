package cg.dimension.aggregatorcoll;

import java.util.Map;

import com.google.common.collect.Maps;

import cg.dimension.aggregator.Aggregator;

/**
 * AggregatorCollection is not a aggregator, but a collection of aggregator which process same type of Bean
 * @author bright
 *
 */
public abstract class AbstractAggregatorChain<B> implements AggregatorCollection<B>
{
  protected Map<String, Aggregator<B, ?, ? extends Number>> nameToAggregator = Maps.newHashMap();
  
  public AbstractAggregatorChain(){}
  
  @SuppressWarnings("unchecked")
  public AbstractAggregatorChain(Aggregator<B, Object, Number> ... aggregators)
  {
    addAggregators(aggregators);
  }
  
  public void addAggregator(Aggregator<B, ?, ? extends Number> aggregator)
  {
    nameToAggregator.put(aggregator.getName(), aggregator);
  }
  
  @SuppressWarnings("unchecked") 
  public void addAggregators(Aggregator<B, Object, Number> ... aggregators)
  {
    for(Aggregator<B, Object, Number> aggregator : aggregators)
      nameToAggregator.put(aggregator.getName(), aggregator);
  }

  @Override
  public Object getValue(String aggregatorName)
  {
    return nameToAggregator.get(aggregatorName).getValue();
  }
}
