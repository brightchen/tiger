package cg.dimension.compute;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.AggregateType;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.criteria.Criteria;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * The assembler of aggregators assemble aggregators and handle them in a unified way to increase the performance.
 * @author bright
 *
 * @param <B>
 */
public class AggregatorsAssembler<B> implements BeanAggregators<B>
{
  protected Map<String, Aggregator<B, ?>> aggregatorMap = Maps.newHashMap();
  //use this structure the share the criteria and BeanPropertyValueGenerator to avoid duplicate computation
  protected Map<PropertyCriteria, Set<BeanPropertyValueGenerator>> criteriaToValueGenerator = Maps.newHashMap();
  protected Map<BeanPropertyValueGenerator, Set<Aggregate>> valueGeneratorToAggregate = Maps.newHashMap();
  
  public AggregatorsAssembler()
  {
  }
  
  /**
   * 
   * @param criteria
   * @param valueGenerator
   * @param aggregateValueType
   * @param aggregateType
   * @return Aggregator in order to get value.
   */
  public Aggregator addAggregator(String name, PropertyCriteria criteria, BeanPropertyValueGenerator valueGenerator, Class aggregateValueType, AggregateType aggregateType)
  {
    if(aggregatorMap.get(name) != null)
      throw new IllegalArgumentException("The aggregate name '" + name + "' already used.");
    Aggregate aggregate = createDefaultAggregate(aggregateType, aggregateValueType);
    SimpleAggregator aggregator = new SimpleAggregator(name, criteria, valueGenerator, aggregate);
    aggregatorMap.put(name, aggregator);
    
    if(criteriaToValueGenerator.get(criteria) == null)
    {
      criteriaToValueGenerator.put(criteria, Sets.<BeanPropertyValueGenerator>newHashSet());
    }
    criteriaToValueGenerator.get(criteria).add(valueGenerator);
    
    if(valueGeneratorToAggregate.get(valueGenerator) == null)
    {
      valueGeneratorToAggregate.put(valueGenerator, Sets.<Aggregate>newHashSet());
    }
    valueGeneratorToAggregate.get(valueGenerator).add(aggregate);
    
    return aggregator;
  }
  
  public void addAggregator(AssembleAggregator<B, ?> aggregator)
  {
    if(aggregatorMap.get(aggregator.getName()) != null)
      throw new IllegalArgumentException("The aggregate name '" + aggregator.getName() + "' already used.");
    aggregatorMap.put(aggregator.getName(), aggregator);
    
    PropertyCriteria criteria = aggregator.getCriteria();
    if(criteriaToValueGenerator.get(criteria) == null)
    {
      criteriaToValueGenerator.put(criteria, Sets.<BeanPropertyValueGenerator>newHashSet());
    }
    
    BeanPropertyValueGenerator valueGenerator = aggregator.getValueGenerator();
    criteriaToValueGenerator.get(criteria).add(valueGenerator);
    
    if(valueGeneratorToAggregate.get(valueGenerator) == null)
    {
      valueGeneratorToAggregate.put(valueGenerator, Sets.<Aggregate>newHashSet());
    }
    valueGeneratorToAggregate.get(valueGenerator).add(aggregator.getAggregate());
  }
  
  
  public void processRecord(B bean)
  {
    for(Map.Entry<PropertyCriteria, Set<BeanPropertyValueGenerator>> criteriaEntry : criteriaToValueGenerator.entrySet())
    {
      if(!criteriaEntry.getKey().matches(bean))
        continue;
      
      for(BeanPropertyValueGenerator valueGenerator : criteriaEntry.getValue())
      {
        Object value = valueGenerator.getPropertyValue(bean);
        
        for(Map.Entry<BeanPropertyValueGenerator, Set<Aggregate>> valueGeneratorEntry : valueGeneratorToAggregate.entrySet())
        {
          for(Aggregate aggregate : valueGeneratorEntry.getValue())
          {
            aggregate.addValue(value);
          }
        }
      }
    }
  }
  
  protected Aggregate createDefaultAggregate(AggregateType aggregateType, Class aggregateValueType)
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
