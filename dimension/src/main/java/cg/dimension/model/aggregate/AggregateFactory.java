package cg.dimension.model.aggregate;

public interface AggregateFactory<V extends Number>
{
  public Aggregate<V> createAggregate();
}
