package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class DefaultTimeSlideAggregator<B> extends AbstractTimeSlideAggregator<B, Long>
{
  public DefaultTimeSlideAggregator(){}
  
  public DefaultTimeSlideAggregator(BeanPropertyValueGenerator<B, Long> valueGenerator, Aggregate<Long> aggregate)
  {
    init(valueGenerator, aggregate);
  }
  
  public void init(BeanPropertyValueGenerator<B, Long> valueGenerator, Aggregate<Long> aggregate)
  {
    this.setValueGenerator(valueGenerator);
    this.setAggregate(aggregate);
  }
  
  protected BeanPropertyValueGenerator<B, Long> valueGenerator;    //related to a property (bean/property)
  protected Aggregate<Long> aggregate;
  
  @Override
  protected Aggregator<B, Long> createSubAggregator(long bucketBeginTime, long bucketTimeSpan, int index)
  {
    return new TimeBucketAggregator<B>(name, valueGenerator, aggregate, bucketBeginTime, bucketTimeSpan);
  }

  public BeanPropertyValueGenerator<B, Long> getValueGenerator()
  {
    return valueGenerator;
  }

  public void setValueGenerator(BeanPropertyValueGenerator<B, Long> valueGenerator)
  {
    this.valueGenerator = valueGenerator;
  }

  public Aggregate<Long> getAggregate()
  {
    return aggregate;
  }

  public void setAggregate(Aggregate<Long> aggregate)
  {
    this.aggregate = aggregate;
  }

  
}
