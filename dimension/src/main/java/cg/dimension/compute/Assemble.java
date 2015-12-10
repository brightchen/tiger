package cg.dimension.compute;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public interface Assemble
{
  public PropertyCriteria getCriteria();
  public BeanPropertyValueGenerator getValueGenerator();
  public Aggregate getAggregate();
}
