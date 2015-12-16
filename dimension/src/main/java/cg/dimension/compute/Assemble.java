package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public interface Assemble<B, MV, AV extends Number>
{
  public PropertyCriteria<B, MV> getCriteria();
  public BeanPropertyValueGenerator<B, MV> getMatchValueGenerator();
  public BeanPropertyValueGenerator<B, AV> getAggregateValueGenerator();
  public Aggregate<AV> getAggregate();
}
