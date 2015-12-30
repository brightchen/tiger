package cg.common.generate;

public class Range<T>
{
  public final T from;
  public final T to;

  public Range()
  {
    from = null;
    to = null;
  }
  
  public Range(T from, T to)
  {
    this.from = from;
    this.to = to;
  }
  
}