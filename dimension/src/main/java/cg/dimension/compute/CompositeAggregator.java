package cg.dimension.compute;

import java.util.List;

import com.google.common.collect.Lists;

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
public class CompositeAggregator<B, V> implements Aggregator<B, V>
{
  protected List<Aggregator<B, V>> subAggregators = Lists.newArrayList();
  
  
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
    return null;
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
    // TODO Auto-generated method stub
    
  }

}
