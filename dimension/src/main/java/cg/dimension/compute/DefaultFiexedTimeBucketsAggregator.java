package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class DefaultFiexedTimeBucketsAggregator<B, AV extends Number> extends AbstractFiexedTimeBucketsAggregator<B, Long, AV>
{
  protected BeanPropertyValueGenerator<B, AV> aggregateValueGenerator;    //related to a property (bean/property)
  protected BeanPropertyValueGenerator<B, Long> timeGenerator;
  // the aggregate here is a template aggregate, each sub aggregator should clone it
  protected Aggregate<AV> aggregateTemplate;
  
  public DefaultFiexedTimeBucketsAggregator(){}
  
  public DefaultFiexedTimeBucketsAggregator(BeanPropertyValueGenerator<B, Long> timeGenerator, BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregateTemplate)
  {
    init(timeGenerator, aggregateValueGenerator, aggregateTemplate);
  }
  
  public void init(BeanPropertyValueGenerator<B, Long> timeGenerator, BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregateTemplate)
  {
    this.setTimeGenerator(timeGenerator);
    this.setAggregateValueGenerator(aggregateValueGenerator);
    this.setAggregateTemplate(aggregateTemplate);
  }

  @Override
  protected Aggregator<B, Long, AV> createSubAggregator(long bucketBeginTime, long bucketTimeSpan, int index)
  {
    return new TimeBucketAggregator<B, AV>(name, timeGenerator, aggregateValueGenerator , aggregateTemplate.cloneMe(), bucketBeginTime, bucketBeginTime + bucketTimeSpan );
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

  public Aggregate<AV> getAggregateTemplate()
  {
    return aggregateTemplate;
  }

  public void setAggregateTemplate(Aggregate<AV> aggregateTemplate)
  {
    this.aggregateTemplate = aggregateTemplate;
  }

  
}
