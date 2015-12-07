package cg.common;

public interface ValueSetter<T>
{
  public T setValue(double value);
  
  public static class LongSetter implements ValueSetter<Long>
  {
    public Long setValue(double value)
    {
      return (long)value;
    }
  }
  public static class IntegerSetter implements ValueSetter<Integer>
  {
    public Integer setValue(double value)
    {
      return (int)value;
    }
  }
  public static class ShortSetter implements ValueSetter<Short>
  {
    public Short setValue(double value)
    {
      return (short)value;
    }
  }
  public static class ByteSetter implements ValueSetter<Byte>
  {
    public Byte setValue(double value)
    {
      return (byte)value;
    }
  }
  public static class FloatSetter implements ValueSetter<Float>
  {
    public Float setValue(double value)
    {
      return (float)value;
    }
  }
  public static class DoubleSetter implements ValueSetter<Double>
  {
    public Double setValue(double value)
    {
      return (Double)value;
    }
  }
}
