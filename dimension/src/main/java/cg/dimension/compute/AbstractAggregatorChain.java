package cg.dimension.compute;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * AggregatorChain is not a aggregator, but a chain of aggregator which process same type of Bean
 * @author bright
 *
 */
public abstract class AbstractAggregatorChain<B> implements BeanAggregators<B>
{
  protected Map<String, Aggregator<B, ?>> nameToAggregator = Maps.newHashMap();
  
  public AbstractAggregatorChain(){}
  
  @SuppressWarnings("unchecked")
  public AbstractAggregatorChain(Aggregator<B, ?> ... aggregators)
  {
    addAggregators(aggregators);
  }
  
  public void addAggregator(Aggregator<B, ?> aggregator)
  {
    nameToAggregator.put(aggregator.getName(), aggregator);
  }
  
  @SuppressWarnings("unchecked") 
  public void addAggregators(Aggregator<B, ?> ... aggregators)
  {
    for(Aggregator<B, ?> aggregator : aggregators)
      nameToAggregator.put(aggregator.getName(), aggregator);
  }

  @Override
  public Object getValue(String aggregatorName)
  {
    return nameToAggregator.get(aggregatorName).getValue();
  }
}
