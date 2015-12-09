package cg.dimension.compute;

import cg.dimension.model.Range;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.criteria.RangeCriteria;
import cg.dimension.model.matcher.RangeMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class TimeBucketAggregator<B> extends SimpleAggregator<B>
{
  protected long bucketBeginTime;
  protected long bucketTimeSpan;
  
  public TimeBucketAggregator(){};
  
  public void init(String name, BeanPropertyValueGenerator<B, Long> valueGenerator, Aggregate aggregate,
      long bucketBeginTime, long bucketTimeSpan)
  {
    RangeMatcher<Long> matcher = new RangeMatcher<Long>(new Range<Long>(bucketBeginTime, bucketTimeSpan));
    PropertyCriteria<B, Long> criteria = new PropertyCriteria<B, Long>(valueGenerator, matcher);
    super.init(name, criteria, valueGenerator, aggregate);
    this.bucketBeginTime = bucketBeginTime;
    this.bucketTimeSpan = bucketTimeSpan;
  }
  
}
