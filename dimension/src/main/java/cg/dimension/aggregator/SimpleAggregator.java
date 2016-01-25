package cg.dimension.aggregator;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * SimpleAggregator use the criteria to match the bean, and use the aggregateValueGenerator to generate the value for aggregating. 
 * @author bright
 *
 * @param <B>
 * @param <V> the type of value for match and aggregate
 */
public class SimpleAggregator<B, V extends Number, K> implements AssembleAggregator<B, V, V, K>
{
  protected String name;
  protected PropertyCriteria<B, V, K> criteria;
  protected BeanPropertyValueGenerator<B, V> aggregateValueGenerator;    //related to a property (bean/property), for both match value and aggregate value
  protected Aggregate<V> aggregate;
  
  public SimpleAggregator(){}
  
  public SimpleAggregator(String name, PropertyCriteria<B, V, K> criteria, 
      BeanPropertyValueGenerator<B, V> valueGenerator, Aggregate<V> aggregate)
  {
    init(name, criteria, valueGenerator, aggregate);
  }

  public void init(String name, PropertyCriteria<B, V, K> criteria, BeanPropertyValueGenerator<B, V> valueGenerator, Aggregate<V> aggregate)
  {
    this.name = name;
    this.setCriteria(criteria);
    this.setAggregateValueGenerator(valueGenerator);
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
    V value = (V)aggregateValueGenerator.getValue(bean);
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
  
  public PropertyCriteria<B, V, K> getCriteria()
  {
    return criteria;
  }

  public void setCriteria(PropertyCriteria<B, V, K> criteria)
  {
    this.criteria = criteria;
  }

  public void setAggregateValueGenerator(BeanPropertyValueGenerator<B, V> valueGenerator)
  {
    this.aggregateValueGenerator = valueGenerator;
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
    return criteria.getMatchValueGenerator();
  }

  @Override
  public BeanPropertyValueGenerator<B, V> getAggregateValueGenerator()
  {
    return aggregateValueGenerator;
  }

  @Override
  public K getKey()
  {
    return criteria.getMatcher().getKey();
  }

}
