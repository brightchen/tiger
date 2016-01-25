package cg.dimension.group;

import cg.dimension.model.CloneableBean;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.matcher.Matcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * 
 * @author bright
 *
 * @param <T> The class which AbstractGroupAggregate can clone to
 * @param <B>
 * @param <MV>
 * @param <AV>
 */
public abstract class AbstractValueMatcherDynamicGroupAggregate<T extends CloneableBean<T>, B, MV, AV extends Number, K> 
    extends AbstractValueMatcherDynamicGroup<T, B, MV, K>
{
  protected BeanPropertyValueGenerator<B, AV> aggregatePropertyValueGenerator;
  protected Aggregate<AV> aggregate;
  
  public AbstractValueMatcherDynamicGroupAggregate(){}
  
  public AbstractValueMatcherDynamicGroupAggregate<T, B, MV, AV, K> withMatchPropertyValueGenerator(BeanPropertyValueGenerator<B, MV> matchPropertyValueGenerator)
  {
    this.setMatchPropertyValueGenerator(matchPropertyValueGenerator);
    return this;
  }
  
  public AbstractValueMatcherDynamicGroupAggregate<T, B, MV, AV, K> withAggregatePropertyValueGenerator(BeanPropertyValueGenerator<B, AV> aggregatePropertyValueGenerator)
  {
    this.setAggregatePropertyValueGenerator(aggregatePropertyValueGenerator);
    return this;
  }
  
  public AbstractValueMatcherDynamicGroupAggregate<T, B, MV, AV, K> withAggregate(Aggregate<AV> aggregate)
  {
    this.setAggregate(aggregate);
    return  this;
  }
  
  
  @Override
  protected void handleMatchedBean(B bean, MV value)
  {
    AV aggregateValue = aggregatePropertyValueGenerator.getValue(bean);
    // handle the value with aggregate
    aggregate.addValue(aggregateValue);
  }

  public BeanPropertyValueGenerator<B, AV> getAggregatePropertyValueGenerator()
  {
    return aggregatePropertyValueGenerator;
  }

  public void setAggregatePropertyValueGenerator(BeanPropertyValueGenerator<B, AV> aggregatePropertyValueGenerator)
  {
    this.aggregatePropertyValueGenerator = aggregatePropertyValueGenerator;
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
