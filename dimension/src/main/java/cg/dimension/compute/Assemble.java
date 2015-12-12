package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public interface Assemble<B, V>
{
  public PropertyCriteria<B, V> getCriteria();
  public BeanPropertyValueGenerator<B, V> getValueGenerator();
  public Aggregate<V> getAggregate();
}
