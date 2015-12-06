package cg.dimension.model.matcher;

public class MatcherAnd<V> extends MatcherList<V> implements Matcher<V>
{
  @Override
  public boolean matches(V value)
  {
    if(matchers == null || matchers.isEmpty())
      return true;
    for(Matcher<V> matcher : matchers)
    {
      if(!matcher.matches(value))
        return false;
    }
    return true;
  }

}