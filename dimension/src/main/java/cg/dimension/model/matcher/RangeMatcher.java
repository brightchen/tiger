package cg.dimension.model.matcher;

import cg.common.generate.Range;

public class RangeMatcher<V extends Comparable<V>> implements TypicalValueMatcherSpec<RangeMatcher<V>, Range<V>, V, Range<V>>
{
  protected Range<V> range;
  
  public RangeMatcher(){}
  
  public RangeMatcher(Range<V> range)
  {
    this.setRange(range);
  }
  
  public RangeMatcher<V> withRange(Range<V> range)
  {
    this.setRange(range);
    return this;
  }
  
  public RangeMatcher<V> withRange(V rangeFrom, V rangeTo)
  {
    this.setRange(new Range<V>(rangeFrom, rangeTo));
    return this;
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

  @Override
  public RangeMatcher<V> cloneMe()
  {
    return new RangeMatcher<V>(range);
  }

  @Override
  public void injectExpectValue(Range<V> value)
  {
    this.range = value;
  }

  @Override
  public Range<V> getKey()
  {
    return range;
  }
}
