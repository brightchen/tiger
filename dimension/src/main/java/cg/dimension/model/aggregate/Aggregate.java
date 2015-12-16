package cg.dimension.model.aggregate;

public interface Aggregate<T extends Number> extends Cloneable<Aggregate<T>>
{
  public T getValue();
  public void addValue(T value);

}
