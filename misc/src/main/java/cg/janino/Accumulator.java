package cg.janino;

import java.util.Map;

import com.google.common.collect.Maps;

public interface Accumulator<T extends Number>
{
  public <V> void add(Class<V> type, V adden);
  
  public T getValue();
  
  public static class AccumulatorResource
  {
    protected static Map<Class, Value> typeToValue = Maps.newHashMap();
    static
    {
      typeToValue.put(Integer.class, new Value.ValueInteger());
      typeToValue.put(Float.class, new Value.ValueFloat());
      typeToValue.put(Double.class, new Value.ValueDouble());
    }
  }
  
  public static class AccumulatorLong extends AccumulatorResource implements Accumulator<Long>
  {
    protected long value = 0;
    
//    public <V extends Number> void add(Class<V> type, V adden)
//    {
//      value += AccumulatorResource.typeToValue.get(type).value(adden);
//    }

    public Long getValue()
    {
      // TODO Auto-generated method stub
      return null;
    }

    public <V> void add(Class<V> type, V adden)
    {
      // TODO Auto-generated method stub
      
    }
    
  }
  
  public interface Value<V extends Number>
  {
    public V value(V value);
    
    public static class ValueInteger implements Value<Integer>
    {
      public Integer value(Integer value)
      {
        return value;
      }
    }
    public static class ValueFloat implements Value<Float>
    {
      public Float value(Float value)
      {
        return value;
      }
    }
    public static class ValueDouble implements Value<Double>
    {
      public Double value(Double value)
      {
        return value;
      }
    }
  }
  
}
