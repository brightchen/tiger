package cg.dimension.compute;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.AggregateType;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class DimensionComputation<T>
{
  protected Map<String, SimpleAggregator<T>> aggregatorMap = Maps.newHashMap();
  //use this structure the share the criteria and BeanPropertyValueGenerator to avoid duplicate computation
  protected Map<PropertyCriteria, Set<BeanPropertyValueGenerator>> criteriaToValueGenerator = Maps.newHashMap();
  protected Map<BeanPropertyValueGenerator, Set<Aggregate>> valueGeneratorToAggregate = Maps.newHashMap();
  
  public DimensionComputation()
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
  public SimpleAggregator addAggregator(String name, PropertyCriteria criteria, BeanPropertyValueGenerator valueGenerator, Class aggregateValueType, AggregateType aggregateType)
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
  
  public void processRecord(T bean)
  {
    processRecordByAggregator(bean);
  }
  
  public void processRecordAsWhole(T bean)
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
  
  public void processRecordByAggregator(T bean)
  {
    for(Map.Entry<String, SimpleAggregator<T>> aggregatorEntry : aggregatorMap.entrySet())
    {
      aggregatorEntry.getValue().processBean(bean);
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
