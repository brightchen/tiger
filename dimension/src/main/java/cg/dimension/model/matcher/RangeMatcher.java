package cg.dimension.model.matcher;

import cg.dimension.model.Range;

public class RangeMatcher<V> implements Matcher<V>
{
  protected Range<V> range;
  
  public RangeMatcher(){}
  
  public RangeMatcher(Range<V> range)
  {
    this.setRange(range);
  }
  
  //@Override
  public boolean matches(V value)
  {
    return range.isInRange(value);
  }

  public Range<V> getRange()
  {
    return range;
  }

  public void setRange(Range<V> range)
  {
    this.range = range;
  }
}
