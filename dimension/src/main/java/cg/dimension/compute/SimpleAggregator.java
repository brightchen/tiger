package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

@SuppressWarnings("rawtypes")
public class SimpleAggregator<T> implements Aggregator<T, Object>
{
  protected String name;
  protected PropertyCriteria criteria;
  protected BeanPropertyValueGenerator valueGenerator;    //related to a property (bean/property)
  protected Aggregate aggregate;
  
  public SimpleAggregator(String name, PropertyCriteria criteria, BeanPropertyValueGenerator valueGenerator, Aggregate aggregate)
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
  public void processMatchedValue(Object value)
  {
    aggregate.addValue(value);
  }
  
  @Override
  public void processBean(T bean)
  {
    if(!criteria.matches(bean))
      return;
    Object value = valueGenerator.getPropertyValue(bean);
    aggregate.addValue(value);
  }
  

  @Override
  public void addValue(Object value)
  {
    aggregate.addValue(value);    
  }
  
  
  @Override
  public Object getValue()
  {
    return aggregate.getValue();
  }
  
  public PropertyCriteria getCriteria()
  {
    return criteria;
  }

  public void setCriteria(PropertyCriteria criteria)
  {
    this.criteria = criteria;
  }

  public BeanPropertyValueGenerator getValueGenerator()
  {
    return valueGenerator;
  }

  public void setValueGenerator(BeanPropertyValueGenerator valueGenerator)
  {
    this.valueGenerator = valueGenerator;
  }

  public Aggregate getAggregate()
  {
    return aggregate;
  }

  public void setAggregate(Aggregate aggregate)
  {
    this.aggregate = aggregate;
  }

  @Override
  public boolean matches(Object value)
  {
    // TODO Auto-generated method stub
    return false;
  }

  
}
