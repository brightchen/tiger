package cg.dimension.handler;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.CloneableAggregate;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * Handle the bean by aggregate
 * 
 * @author bright
 *
 */
public class BeanAggregateHandler<B, AV extends Number> 
    implements BeanHandler<B>, CloneableBean<BeanAggregateHandler<B, AV>>
{
  protected BeanPropertyValueGenerator<B, AV> aggregateValueGenerator;
  protected Aggregate<AV> aggregate;
  
  public BeanAggregateHandler<B, AV> withAggregateValueGenerator(BeanPropertyValueGenerator<B, AV> aggregateValueGenerator)
  {
    this.aggregateValueGenerator = aggregateValueGenerator;
    return this;
  }
  
  public BeanAggregateHandler<B, AV> withAggregate(Aggregate<AV> aggregate)
  {
    this.aggregate = aggregate;
    return this;
  }
  
  @Override
  public void handleBean(B bean)
  {
    AV aggregateValue = aggregateValueGenerator.getValue(bean);
    aggregate.addValue(aggregateValue);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public BeanAggregateHandler<B, AV> cloneMe()
  {
    BeanAggregateHandler<B, AV> handler = new BeanAggregateHandler<>();
    if(aggregate instanceof CloneableAggregate)
      handler.withAggregate((Aggregate)((CloneableAggregate)aggregate).cloneMe());
    else
      handler.withAggregate(aggregate);
    handler.withAggregateValueGenerator(aggregateValueGenerator);
    return handler;
  }

  public BeanPropertyValueGenerator<B, AV> getAggregateValueGenerator()
  {
    return aggregateValueGenerator;
  }

  public void setAggregateValueGenerator(BeanPropertyValueGenerator<B, AV> aggregateValueGenerator)
  {
    this.aggregateValueGenerator = aggregateValueGenerator;
  }

  public Aggregate<AV> getAggregate()
  {
    return aggregate;
  }

  public void setAggregate(Aggregate<AV> aggregate)
  {
    this.aggregate = aggregate;
  }

  
}
