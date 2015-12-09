package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.matcher.Matcher;

/**
 * Aggregator compose criteria, aggregate and value provider
 * @author bright
 *
 * @param <B>
 * @param <V>
 */
public interface Aggregator<B, V extends Number> extends Matcher<V>, Aggregate<V>
{
  /**
   * this assume the value already match the criteria.
   * So the value will not be checked.
   * This method can be called if lots of Aggregator share the same criteria and save the time of duplicated checking criteria
   * @param value
   */
  public void processMatchedValue(V value);
  
  public void processBean(B bean);
  
}
