package cg.dimension.compute;

/**
 * The aggregators of same type of bean
 * @author bright
 *
 */
public interface BeanAggregators<B>
{
  public void processRecord(B bean);
  public Object getValue(String aggregatorName);
}
