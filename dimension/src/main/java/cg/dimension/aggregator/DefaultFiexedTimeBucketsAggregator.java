package cg.dimension.aggregator;

import cg.common.generate.Range;
import cg.dimension.model.aggregate.AggregateFactory;
import cg.dimension.model.aggregate.CloneableAggregate;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * TODO: The TimeBucketsAggregator should be a special case of group.
 * TODO: considering merge group with Aggregator.
 * 
 * @author bright
 *
 * @param <B>
 * @param <AV>
 */
public class DefaultFiexedTimeBucketsAggregator<B, AV extends Number, K> extends AbstractFiexedTimeBucketsAggregator<B, Long, AV, K>
{
  protected BeanPropertyValueGenerator<B, AV> aggregateValueGenerator;    //related to a property (bean/property)
  protected BeanPropertyValueGenerator<B, Long> timeGenerator;
  // the aggregate here is a template aggregate, each sub aggregator should clone it.
  // instead of use aggregateTemplate, can use AggregateFactory, it is more generic and make sense.
  protected AggregateFactory<? extends CloneableAggregate<?, AV>, AV> aggregateFactory;
  //protected Aggregate<AV> aggregateTemplate;
  
  public DefaultFiexedTimeBucketsAggregator(){}
  
  public DefaultFiexedTimeBucketsAggregator(BeanPropertyValueGenerator<B, Long> timeGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, AggregateFactory<? extends CloneableAggregate<?, AV>, AV> aggregateFactory)
  {
    init(timeGenerator, aggregateValueGenerator, aggregateFactory);
  }
  
  public void init(BeanPropertyValueGenerator<B, Long> timeGenerator, BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, 
      AggregateFactory<? extends CloneableAggregate<?, AV>, AV> aggregateFactory)
  {
    this.setTimeGenerator(timeGenerator);
    this.setAggregateValueGenerator(aggregateValueGenerator);
    this.setAggregateFactory(aggregateFactory);
  }

  @Override
  protected Aggregator<B, Long, AV, Range<Long>> createSubAggregator(long bucketBeginTime, long bucketTimeSpan, int index)
  {
    return new TimeBucketAggregator<B, AV>(name, timeGenerator, aggregateValueGenerator , 
        aggregateFactory.createAggregate(), bucketBeginTime, bucketBeginTime + bucketTimeSpan );
  }

  public BeanPropertyValueGenerator<B, AV> getAggregateValueGenerator()
  {
    return aggregateValueGenerator;
  }

  public void setAggregateValueGenerator(BeanPropertyValueGenerator<B, AV> aggregateValueGenerator)
  {
    this.aggregateValueGenerator = aggregateValueGenerator;
  }

  public BeanPropertyValueGenerator<B, Long> getTimeGenerator()
  {
    return timeGenerator;
  }

  public void setTimeGenerator(BeanPropertyValueGenerator<B, Long> timeGenerator)
  {
    this.timeGenerator = timeGenerator;
  }

  public AggregateFactory<? extends CloneableAggregate<?, AV>, AV> getAggregateFactory()
  {
    return aggregateFactory;
  }

  public void setAggregateFactory(AggregateFactory<? extends CloneableAggregate<?, AV>, AV> aggregateFactory)
  {
    this.aggregateFactory = aggregateFactory;
  }
  
}
