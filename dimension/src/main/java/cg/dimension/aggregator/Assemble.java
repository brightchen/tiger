package cg.dimension.aggregator;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public interface Assemble<B, MV, AV extends Number, K>
{
  public PropertyCriteria<B, MV, K> getCriteria();
  public BeanPropertyValueGenerator<B, MV> getMatchValueGenerator();
  public BeanPropertyValueGenerator<B, AV> getAggregateValueGenerator();
  public Aggregate<AV> getAggregate();
}
