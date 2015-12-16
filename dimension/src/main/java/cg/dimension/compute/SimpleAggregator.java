package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * SimpleAggregator is the aggregator which use the same property of bean to match and calculate the aggregate
 * @author bright
 *
 * @param <B>
 * @param <V> the type of value for match and aggregate
 */
public class SimpleAggregator<B, V extends Number> implements AssembleAggregator<B, V, V>
{
  protected String name;
  protected PropertyCriteria<B, V> criteria;
  protected BeanPropertyValueGenerator<B, V> valueGenerator;    //related to a property (bean/property), for both match value and aggregate value
  protected Aggregate<V> aggregate;
  
  public SimpleAggregator(){}
  
  public SimpleAggregator(String name, PropertyCriteria<B, V> criteria, 
      BeanPropertyValueGenerator<B, V> valueGenerator, Aggregate<V> aggregate)
  {
    init(name, criteria, valueGenerator, aggregate);
  }

  public void init(String name, PropertyCriteria<B, V> criteria, BeanPropertyValueGenerator<B, V> valueGenerator, Aggregate<V> aggregate)
  {
    this.name = name;
    this.setCriteria(criteria);
    this.setValueGenerator(valueGenerator);
    this.setAggregate(aggregate);
  }
  
  /**
   * this assume the value already match the criteria.
   * So the value will not be checked.
   * This method can be called if lots of Aggregator share the same criteria and save the time of duplicated checking criteria
   * @param value
   */
  @Override
  public void processMatchedValue(V value)
  {
    aggregate.addValue(value);
  }
  
  @Override
  public void processBean(B bean)
  {
    if(!criteria.matches(bean))
      return;
    V value = (V)valueGenerator.getPropertyValue(bean);
    aggregate.addValue(value);
  }
  
  @Override
  public boolean matches(V value)
  {
    return criteria.getMatcher().matches(value);
  }
  
  @Override
  public V getValue()
  {
    return aggregate.getValue();
  }
  
  public PropertyCriteria<B, V> getCriteria()
  {
    return criteria;
  }

  public void setCriteria(PropertyCriteria<B, V> criteria)
  {
    this.criteria = criteria;
  }

  public BeanPropertyValueGenerator<B, V> getValueGenerator()
  {
    return valueGenerator;
  }

  public void setValueGenerator(BeanPropertyValueGenerator<B, V> valueGenerator)
  {
    this.valueGenerator = valueGenerator;
  }

  public Aggregate<V> getAggregate()
  {
    return aggregate;
  }

  public void setAggregate(Aggregate<V> aggregate)
  {
    this.aggregate = aggregate;
  }


  @Override
  public void addValue(Number value)
  {
    // TODO Auto-generated method stub
    
  }

  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }

  @Override
  public BeanPropertyValueGenerator<B, V> getMatchValueGenerator()
  {
    return valueGenerator;
  }

  @Override
  public BeanPropertyValueGenerator<B, V> getAggregateValueGenerator()
  {
    return valueGenerator;
  }
}
