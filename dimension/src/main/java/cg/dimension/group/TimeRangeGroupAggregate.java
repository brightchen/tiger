package cg.dimension.group;

import cg.common.generate.Range;
import cg.dimension.model.matcher.MatcherTimeRangeMapper;
import cg.dimension.model.matcher.MatcherTimeRangeMapper.TimeRangePolicy;
import cg.dimension.model.matcher.RangeMatcher;

public class TimeRangeGroupAggregate<B, AV extends Number>  
    extends DefaultGroupAggregate<RangeMatcher<Long>, Range<Long>, B, Long, AV>
{
  public TimeRangeGroupAggregate(){}
  
  public TimeRangeGroupAggregate<B, AV> withTimeRangePolicy(TimeRangePolicy timeRangePolicy)
  {
    matcherValueMapper = new MatcherTimeRangeMapper().withTimeRangePolicy(timeRangePolicy);
    return this;
  }
}
