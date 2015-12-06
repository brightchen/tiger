package cg.common;

public interface AddTo<R, T>
{
  public R addTo(R orgValue, T value);
  
  public static class LongAddToLong implements AddTo<Long,Long>
  {
    public Long addTo(Long orgValue, Long value)
    {
      return orgValue + value;
    }
  }
  
  public static class IntegerAddToLong implements AddTo<Long,Integer>
  {
    public Long addTo(Long orgValue, Integer value)
    {
      return orgValue + value;
    }
  }
  
  public static class ShortAddToLong implements AddTo<Long,Short>
  {
    public Long addTo(Long orgValue, Short value)
    {
      return orgValue + value;
    }
  }
  
  public static class ByteAddToLong implements AddTo<Long,Byte>
  {
    public Long addTo(Long orgValue, Byte value)
    {
      return orgValue + value;
    }
  }
  
  public static class IntegerAddToInteger implements AddTo<Integer,Integer>
  {
    public Integer addTo(Integer orgValue, Integer value)
    {
      return orgValue + value;
    }
  }
  
  public static class ShortAddToInteger implements AddTo<Integer,Short>
  {
    public Integer addTo(Integer orgValue, Short value)
    {
      return orgValue + value;
    }
  }
  
  public static class ByteAddToInteger implements AddTo<Integer,Byte>
  {
    public Integer addTo(Integer orgValue, Byte value)
    {
      return orgValue + value;
    }
  }
  
  
  public static class FloatAddToFloat implements AddTo<Float,Float>
  {
    public Float addTo(Float orgValue, Float value)
    {
      return orgValue + value;
    }
  }
  
  public static class FloatAddToDouble implements AddTo<Double,Float>
  {
    public Double addTo(Double orgValue, Float value)
    {
      return orgValue + value;
    }
  }
  
  public static class DoubleAddToDouble implements AddTo<Double,Double>
  {
    public Double addTo(Double orgValue, Double value)
    {
      return orgValue + value;
    }
  }
}