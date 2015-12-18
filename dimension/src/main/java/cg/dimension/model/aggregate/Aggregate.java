package cg.dimension.model.aggregate;

public interface Aggregate<V extends Number>
{
  public V getValue();
  public void addValue(V value);

}
