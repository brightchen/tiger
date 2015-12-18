package cg.dimension.aggregatorcoll;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cg.dimension.aggregator.Aggregator;
import cg.dimension.aggregator.AssembleAggregator;
import cg.dimension.aggregator.SimpleAggregator;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.AggregateType;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.criteria.Criteria;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * The AggregatorMapIntegrator integrates aggregators and handle them in a unified way to increase the performance.
 * This class implements the logic by keep map with criteria to value-generator, and value-generator to aggregate
 * 
 * 
 * @author bright
 *
 * @param <B>
 */
public class AggregatorMapIntegrator<B> implements AggregatorCollection<B>
{
  protected Map<String, Aggregator<B, ?, ? extends Number>> aggregatorMap = Maps.newHashMap();
  //use this structure the share the criteria and BeanPropertyValueGenerator to avoid duplicate computation
  protected Map<PropertyCriteria<B, Object>, Set<BeanPropertyValueGenerator<B, Object>>> criteriaToMatchValueGenerator = Maps.newHashMap();
  protected Map<BeanPropertyValueGenerator<B, Number>, Set<Aggregate<Number>>> aggregateValueGeneratorToAggregate = Maps.newHashMap();
  
  public AggregatorMapIntegrator()
  {
  }
  
  /**
   * 
   * @param criteria
   * @param matchValueGenerator
   * @param aggregateValueType
   * @param aggregateType
   * @return Aggregator in order to get value.
   */
  public <MV, AV extends Number> Aggregator<B, MV, AV> addAggregator(String name, PropertyCriteria<B, MV> criteria, 
      BeanPropertyValueGenerator<B, MV> matchValueGenerator, 
      BeanPropertyValueGenerator<B, AV> aggregateValueGenerator,
      Class<AV> aggregateValueType, AggregateType aggregateType)
  {
    if(aggregatorMap.get(name) != null)
      throw new IllegalArgumentException("The aggregate name '" + name + "' already used.");
    Aggregate<AV> aggregate = createDefaultAggregate(aggregateType, aggregateValueType);
    SimpleAggregator aggregator = new SimpleAggregator(name, criteria, matchValueGenerator, aggregate);
    aggregatorMap.put(name, aggregator);
    
    if(criteriaToMatchValueGenerator.get(criteria) == null)
    {
      criteriaToMatchValueGenerator.put((PropertyCriteria<B, Object>)criteria, Sets.<BeanPropertyValueGenerator<B, Object>>newHashSet());
    }
    criteriaToMatchValueGenerator.get(criteria).add((BeanPropertyValueGenerator<B, Object>)matchValueGenerator);
    
    if(aggregateValueGeneratorToAggregate.get(aggregateValueGenerator) == null)
    {
      aggregateValueGeneratorToAggregate.put((BeanPropertyValueGenerator<B, Number>)aggregateValueGenerator, 
          Sets.<Aggregate<Number>>newHashSet());
    }
    aggregateValueGeneratorToAggregate.get(matchValueGenerator).add((Aggregate<Number>)aggregate);
    
    return aggregator;
  }
  
  public void addAggregator(AssembleAggregator<B, Object, Number> aggregator)
  {
    if(aggregatorMap.get(aggregator.getName()) != null)
      throw new IllegalArgumentException("The aggregate name '" + aggregator.getName() + "' already used.");
    aggregatorMap.put(aggregator.getName(), aggregator);
    
    PropertyCriteria<B, Object> criteria = aggregator.getCriteria();
    if(criteriaToMatchValueGenerator.get(criteria) == null)
    {
      criteriaToMatchValueGenerator.put(criteria, Sets.<BeanPropertyValueGenerator<B, Object>>newHashSet());
    }
    BeanPropertyValueGenerator matcheValueGenerator = aggregator.getMatchValueGenerator();
    criteriaToMatchValueGenerator.get(criteria).add(matcheValueGenerator);
    
    BeanPropertyValueGenerator<B, Number> aggregateValueGenerator = aggregator.getAggregateValueGenerator();
    if(aggregateValueGeneratorToAggregate.get(aggregateValueGenerator) == null)
    {
      aggregateValueGeneratorToAggregate.put(aggregateValueGenerator, Sets.<Aggregate<Number>>newHashSet());
    }
    aggregateValueGeneratorToAggregate.get(aggregateValueGenerator).add(aggregator.getAggregate());
  }
  
  @Override
  public void processBean(B bean)
  {
    for(Map.Entry<PropertyCriteria<B, Object>, Set<BeanPropertyValueGenerator<B, Object>>> criteriaEntry : criteriaToMatchValueGenerator.entrySet())
    {
      if(!criteriaEntry.getKey().matches(bean))
        continue;
      
      for(BeanPropertyValueGenerator<B, Object> valueGenerator : criteriaEntry.getValue())
      {
        Number value = (Number)valueGenerator.getValue(bean);
        
        for(Map.Entry<BeanPropertyValueGenerator<B, Number>, Set<Aggregate<Number>>> valueGeneratorEntry : aggregateValueGeneratorToAggregate.entrySet())
        {
          for(Aggregate<Number> aggregate : valueGeneratorEntry.getValue())
          {
            aggregate.addValue(value);
          }
        }
      }
    }
  }
  
  protected <AV extends Number> Aggregate<AV> createDefaultAggregate(AggregateType aggregateType, Class<AV> aggregateValueType)
  {
    switch(aggregateType)
    {
      case SUM:
        return new IncrementalAggregateSum(aggregateValueType);
    }
    throw new IllegalArgumentException("Unsupported.");
  }
  
  public Object getValue(String aggregatorName)
  {
    return aggregatorMap.get(aggregatorName).getValue();
  }

}
