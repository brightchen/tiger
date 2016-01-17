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
  
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public boolean isInRange(T value)
  {
    if(value instanceof Comparable)
    {
      int result = ((Comparable)value).compareTo(from);
      if(result == 0)
        return true;
      return (Math.signum(result) != Math.signum(((Comparable)value).compareTo(to)) );
    }
    throw new IllegalStateException("The range is not comparable");
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((from == null) ? 0 : from.hashCode());
    result = prime * result + ((to == null) ? 0 : to.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    @SuppressWarnings("rawtypes")
    Range other = (Range)obj;
    if (from == null) {
      if (other.from != null)
        return false;
    } else if (!from.equals(other.from))
      return false;
    if (to == null) {
      if (other.to != null)
        return false;
    } else if (!to.equals(other.to))
      return false;
    return true;
  }
  
  
}