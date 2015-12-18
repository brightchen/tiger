package cg.dimension.model.aggregate;

/**
 * count can be treated as the special case of sum, which every value is 1;
 * @author bright
 *
 */
public class IncrementalAggregateCount extends IncrementalAggregateSum<Long>
{
  /**
   * for Count, ignore the value of bean
   */
  @Override
  public void addValue(Long value)
  {
    sum++;
  }
}