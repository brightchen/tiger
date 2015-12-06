package cg.janino;

public interface Adder<T>
{
  public T add(T v1, T v2);
  
  public static class ShortAdder implements Adder<Short>
  {
    public Short add(Short v1, Short v2)
    {
      return (short)(v1+v2);
    }
  }
  
  public static class IntegerAdder implements Adder<Integer>
  {
    public Integer add(Integer v1, Integer v2)
    {
      return v1+v2;
    }
  }
  
  public static class FloatAdder implements Adder<Float>
  {
    public Float add(Float v1, Float v2)
    {
      return v1+v2;
    }
  }
  
  public static class DoubleAdder implements Adder<Double>
  {
    public Double add(Double v1, Double v2)
    {
      return v1+v2;
    }
  }
}
