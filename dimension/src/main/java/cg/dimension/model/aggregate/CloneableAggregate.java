package cg.dimension.model.aggregate;

import cg.dimension.model.CloneableBean;

public interface CloneableAggregate<T extends CloneableAggregate<T, V>, V extends Number> extends Aggregate<V>, CloneableBean<T> 
{
}
