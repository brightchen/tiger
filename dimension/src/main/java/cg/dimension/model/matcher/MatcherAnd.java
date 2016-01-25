package cg.dimension.model.matcher;

public class MatcherAnd<V, K> extends MatcherList<V, K> implements Matcher<V, K>
{
  @Override
  public boolean matches(V value)
  {
    if(matchers == null || matchers.isEmpty())
      return true;
    for(Matcher<V, K> matcher : matchers)
    {
      if(!matcher.matches(value))
        return false;
    }
    return true;
  }

  @Override
  public K getKey()
  {
    throw new RuntimeException("Not Supported.");
  }

}