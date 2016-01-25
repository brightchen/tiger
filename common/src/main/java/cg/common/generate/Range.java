package cg.common.generate;

public class Range<T extends Comparable<T>>
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
    if(from.compareTo(to) > 0)
      throw new IllegalStateException("Invalid ragne, <from> should smaller than <to>");
    this.from = from;
    this.to = to;
  }
  
  /**
   * The range start from <from> inclusively and end with <to> exclusively
   * @param value
   * @return
   */
  public boolean isInRange(T value)
  {
    int resultFrom = value.compareTo(from);
    if (resultFrom < 0)
      return false;
    if (resultFrom == 0)
      return true;
    return value.compareTo(to) < 0;
  }

  public void setValue(T from, T to)
  {
    
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

  @Override
  public String toString()
  {
    return "Range[" + from + ", " + to + "]";
  }
  
}