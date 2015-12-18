package cg.dimension.aggregator;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * 
 * @author bright
 *
 * @param <B> the type of bean
 * @param <MV> the type of the value used for match
 * @param <AV> the type of the value for aggregate
 */
public class GeneralAggregator<B, MV, AV extends Number> implements AssembleAggregator<B, MV, AV>
{
  protected String name;
  protected PropertyCriteria<B, MV> criteria;
  protected BeanPropertyValueGenerator<B, AV> aggregateValueGenerator;    //related to a property (bean/property)
  protected BeanPropertyValueGenerator<B, MV> matchValueGenerator;    //related to a property (bean/property)
  protected Aggregate<AV> aggregate;
  
  public GeneralAggregator(){}
  
  public GeneralAggregator(String name, PropertyCriteria<B, MV> criteria, BeanPropertyValueGenerator<B, MV> matchValueGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregate)
  {
    init(name, criteria, matchValueGenerator, aggregateValueGenerator, aggregate);
  }

  public void init(String name, PropertyCriteria<B, MV> criteria, BeanPropertyValueGenerator<B, MV> matchValueGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator, Aggregate<AV> aggregate)
  {
    this.name = name;
    this.setCriteria(criteria);
    this.setMatchValueGenerator(matchValueGenerator);
    this.setAggregateValueGenerator(aggregateValueGenerator);
    this.setAggregate(aggregate);
  }
  
  /**
   * this assume the value already match the criteria.
   * So the value will not be checked.
   * This method can be called if lots of Aggregator share the same criteria and save the time of duplicated checking criteria
   * @param value
   */
  @Override
  public void processMatchedValue(AV value)
  {
    aggregate.addValue(value);
  }
  
  @Override
  public void processBean(B bean)
  {
    if(!criteria.matches(bean))
      return;
    AV value = (AV)aggregateValueGenerator.getValue(bean);
    aggregate.addValue(value);
  }
  
  @Override
  public boolean matches(MV value)
  {
    return criteria.getMatcher().matches(value);
  }
  
  @Override
  public AV getValue()
  {
    return aggregate.getValue();
  }
  

  public PropertyCriteria<B, MV> getCriteria()
  {
    return criteria;
  }

  public void setCriteria(PropertyCriteria<B, MV> criteria)
  {
    this.criteria = criteria;
  }

  public BeanPropertyValueGenerator<B, AV> getValueGenerator()
  {
    return aggregateValueGenerator;
  }

  public void setAggregateValueGenerator(BeanPropertyValueGenerator<B, AV> aggregateValueGenerator)
  {
    this.aggregateValueGenerator = aggregateValueGenerator;
  }

  public void setMatchValueGenerator(BeanPropertyValueGenerator<B, MV> matchValueGenerator)
  {
    this.matchValueGenerator = matchValueGenerator;
  }
  
  public void setAggregate(Aggregate<AV> aggregate)
  {
    this.aggregate = aggregate;
  }

  public Aggregate<AV> getAggregate()
  {
    return aggregate;
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
  public BeanPropertyValueGenerator<B, MV> getMatchValueGenerator()
  {
    return matchValueGenerator;
  }

  @Override
  public BeanPropertyValueGenerator<B, AV> getAggregateValueGenerator()
  {
    return aggregateValueGenerator;
  }

  
}