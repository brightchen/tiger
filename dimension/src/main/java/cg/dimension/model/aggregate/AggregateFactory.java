package cg.dimension.model.aggregate;

public interface AggregateFactory<T extends CloneableAggregate<T, V>, V extends Number>
{
  public T createAggregate();
}
