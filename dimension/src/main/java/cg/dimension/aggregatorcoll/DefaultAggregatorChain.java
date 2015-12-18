package cg.dimension.aggregatorcoll;

import java.util.Map;

import cg.dimension.aggregator.Aggregator;

public class DefaultAggregatorChain<B> extends AbstractAggregatorChain<B>
{
  @Override
  public void processBean(B bean)
  {
    for(Map.Entry<String, Aggregator<B, ?, ? extends Number>> entry : nameToAggregator.entrySet())
    {
      entry.getValue().processBean(bean);
    }
  }
  
}
