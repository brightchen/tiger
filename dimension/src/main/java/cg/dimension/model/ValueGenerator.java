package cg.dimension.model;

public interface ValueGenerator<S, V>
{
  /**
   * get value from the value source
   * @param source
   * @return
   */
  public V getValue(S source);
}
