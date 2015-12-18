package cg.dimension.model.aggregate;

public interface CloneableAggregate<V extends Number> extends Aggregate<V>, Cloneable<Aggregate<V>>   //Cloneable<CloneableAggregate<V>> can't compiled?
{
}
