package cg.dimension.model;

public interface CloneableBean<T extends CloneableBean<T>>
{
  public T cloneMe();
}
