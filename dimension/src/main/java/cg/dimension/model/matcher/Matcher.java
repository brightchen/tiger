package cg.dimension.model.matcher;

public interface Matcher<V, K>
{
  public boolean matches(V value);
  public K getKey();
}
