package cg.dimension.compute;

import java.util.Map;

public class DefaultAggregatorChain<B> extends AbstractAggregatorChain<B>
{
  @Override
  public void processRecord(B bean)
  {
    for(Map.Entry<String, Aggregator<B, ?, ? extends Number>> entry : nameToAggregator.entrySet())
    {
      entry.getValue().processBean(bean);
    }
  }
  
}
