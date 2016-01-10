package cg.dimension.model.matcher;

public class MatcherSameValueMapper<V> implements MatcherValueMapper<V, V>
{

  @Override
  public V getExpectValue(V matchValue)
  {
    return matchValue;
  }

}
