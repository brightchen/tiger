package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.matcher.Matcher;

/**
 * Aggregator compose criteria, aggregate and value provider
 * There are two type of value, the value used to match the criteria, and the value to do the aggregate
 * 
 * @author bright
 *
 * @param <B>
 * @param <V>
 */
public interface Aggregator<B, MV, AV extends Number> extends Matcher<MV>//, Aggregate<AV>
{
  /**
   * this assume the value already match the criteria.
   * So the value will not be checked.
   * This method can be called if lots of Aggregator share the same criteria and save the time of duplicated checking criteria
   * @param value
   */
  public void processMatchedValue(AV value);
  
  public void processBean(B bean);
  
  public String getName();
  
  public AV getValue();
  public void addValue(AV value);

}
