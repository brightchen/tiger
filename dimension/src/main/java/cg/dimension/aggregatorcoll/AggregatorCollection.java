package cg.dimension.aggregatorcoll;

/**
 * The aggregators of same type of bean
 * @author bright
 *
 */
public interface AggregatorCollection<B>
{
  public void processBean(B bean);
  public Object getValue(String aggregatorName);
}
