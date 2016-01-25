package cg.dimension.groupcoll;

/**
 * SimpleMapAutoGenerateGroupCollection is a MapAutoGenerateGroupCollection which key and match value are same type
 * @author bright
 *
 * @param <B>
 * @param <MV>
 */
public class SimpleMapAutoGenerateGroupCollection<B, MV> extends  MapAutoGenerateGroupCollection<B, MV, MV> 
{
  @Override
  protected MV getKey(MV matchValue)
  {
    return matcherValueMapper == null ? matchValue : matcherValueMapper.getExpectValue(matchValue);
  }
}
