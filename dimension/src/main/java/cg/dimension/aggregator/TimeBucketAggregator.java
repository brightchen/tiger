package cg.dimension.aggregator;

import cg.dimension.model.Range;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.matcher.RangeMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class TimeBucketAggregator<B, AV extends Number> extends GeneralAggregator<B, Long, AV>
{
  protected long bucketBeginTime;
  protected long bucketTimeSpan;
  
  public TimeBucketAggregator(){};
  
  public TimeBucketAggregator(String name, BeanPropertyValueGenerator<B, Long> matchValueGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregate,
      long bucketBeginTime, long bucketTimeSpan)
  {
    init(name, matchValueGenerator, aggregateValueGenerator, aggregate, bucketBeginTime, bucketTimeSpan);
  }
  
  public void init(String name, BeanPropertyValueGenerator<B, Long> matchValueGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregate,
      long bucketBeginTime, long bucketTimeSpan)
  {
    RangeMatcher<Long> matcher = new RangeMatcher<Long>(new Range<Long>(bucketBeginTime, bucketTimeSpan));
    PropertyCriteria<B, Long> criteria = new PropertyCriteria<B, Long>(matchValueGenerator, matcher);
    super.init(name, criteria, matchValueGenerator, aggregateValueGenerator, aggregate );
    this.bucketBeginTime = bucketBeginTime;
    this.bucketTimeSpan = bucketTimeSpan;
  }
  
}
