package cg.dimension.model.matcher;

import cg.common.generate.Range;

/**
 * The MatcherTimeRangeMapper should make sure this are no overlap and no spacing between two continuous range.
 * @author bright
 *
 */
public class MatcherTimeRangeMapper implements MatcherValueMapper<Long, Range<Long>>
{
  protected final int MILLI_SECONDS_PER_UNIT = 1000;
  
  public static enum TimeRangePolicy
  {
    PER_DAY(24*60*60),
    PER_HOUR(60*60),
    PER_MINUTE(60),
    PER_SECOND(1);
    
    private TimeRangePolicy(int units)
    {
      this.units = units;
    }
    
    protected long units;
  }
  
  protected TimeRangePolicy timeRangePolicy;
  
  public MatcherTimeRangeMapper withTimeRangePolicy(TimeRangePolicy timeRangePolicy)
  {
    this.setTimeRangePolicy(timeRangePolicy);
    return this;
  }
  
  @Override
  public Range<Long> getExpectValue(Long matchValue)
  {
    long startValue = matchValue - matchValue % (timeRangePolicy.units * MILLI_SECONDS_PER_UNIT);
    return new Range<Long>(startValue, startValue+timeRangePolicy.units * MILLI_SECONDS_PER_UNIT);
  }

  public TimeRangePolicy getTimeRangePolicy()
  {
    return timeRangePolicy;
  }

  public void setTimeRangePolicy(TimeRangePolicy timeRangePolicy)
  {
    this.timeRangePolicy = timeRangePolicy;
  }

  
}
