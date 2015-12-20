package cg.common.math;

import java.util.Map;

import com.google.common.collect.Maps;

public class Calculator
{
  public static class ClassPair
  {
    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((class1 == null) ? 0 : class1.hashCode());
      result = prime * result + ((class2 == null) ? 0 : class2.hashCode());
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
      ClassPair other = (ClassPair)obj;
      if (class1 == null) {
        if (other.class1 != null)
          return false;
      } else if (!class1.equals(other.class1))
        return false;
      if (class2 == null) {
        if (other.class2 != null)
          return false;
      } else if (!class2.equals(other.class2))
        return false;
      return true;
    }

    public final Class class1;
    public final Class class2;

    public ClassPair(Class class1, Class class2)
    {
      this.class1 = class1;
      this.class2 = class2;
    }
  }

  public static Map<Class, AddTo> sameTypeToAdder = Maps.newHashMap();
  static {
    sameTypeToAdder.put(Byte.class, new AddTo.ByteAddToByte());
    sameTypeToAdder.put(Short.class, new AddTo.ShortAddToShort());
    sameTypeToAdder.put(Integer.class, new AddTo.IntegerAddToInteger());
    sameTypeToAdder.put(Long.class, new AddTo.LongAddToLong());
    sameTypeToAdder.put(Float.class, new AddTo.FloatAddToFloat());
    sameTypeToAdder.put(Double.class, new AddTo.DoubleAddToDouble());
  }

  //(AddenType, ResultType) ==> class
  public static Map<ClassPair, AddTo> typePairToAddTo = Maps.newHashMap();

  static {
    {
      ClassPair longLong = new ClassPair(Long.class, Long.class);
      typePairToAddTo.put(longLong, new AddTo.LongAddToLong());
    }
    {
      ClassPair integerLong = new ClassPair(Integer.class, Long.class);
      typePairToAddTo.put(integerLong, new AddTo.IntegerAddToLong());
    }
    {
      ClassPair shortLong = new ClassPair(Short.class, Long.class);
      typePairToAddTo.put(shortLong, new AddTo.ShortAddToLong());
    }
    {
      ClassPair byteLong = new ClassPair(Byte.class, Long.class);
      typePairToAddTo.put(byteLong, new AddTo.ByteAddToLong());
    }
    {
      ClassPair integerInteger = new ClassPair(Integer.class, Integer.class);
      typePairToAddTo.put(integerInteger, new AddTo.IntegerAddToInteger());
    }
    {
      ClassPair shortInteger = new ClassPair(Short.class, Integer.class);
      typePairToAddTo.put(shortInteger, new AddTo.ShortAddToInteger());
    }
    {
      ClassPair byteInteger = new ClassPair(Byte.class, Integer.class);
      typePairToAddTo.put(byteInteger, new AddTo.ByteAddToInteger());
    }
    {
      ClassPair floatFloat = new ClassPair(Float.class, Float.class);
      typePairToAddTo.put(floatFloat, new AddTo.FloatAddToFloat());
    }
    {
      ClassPair floatDouble = new ClassPair(Float.class, Double.class);
      typePairToAddTo.put(floatDouble, new AddTo.FloatAddToDouble());
    }
    {
      ClassPair doubleDouble = new ClassPair(Double.class, Double.class);
      typePairToAddTo.put(doubleDouble, new AddTo.DoubleAddToDouble());
    }
  }

  public static <T extends Number> T add(T value1, T value2)
  {
    return (T)sameTypeToAdder.get(value1.getClass()).addTo(value1, value2);
  }

  public static <T extends Number, R extends Number> R addTo(R orgValue, T value)
  {
    AddTo addTo = typePairToAddTo.get(new ClassPair(value.getClass(), orgValue.getClass()));
    if(addTo == null)
      throw new UnsupportedOperationException("Not Support add " + value.getClass() + " to " + orgValue.getClass());
    return (R)addTo.addTo(orgValue, value);
  }
  
  
  @SuppressWarnings("rawtypes")
  public static Map<Class, ValueSetter> typeToValueSetter = Maps.newHashMap();
  static {
    typeToValueSetter.put(Byte.class, new ValueSetter.ByteSetter());
    typeToValueSetter.put(Short.class, new ValueSetter.ShortSetter());
    typeToValueSetter.put(Integer.class, new ValueSetter.IntegerSetter());
    typeToValueSetter.put(Long.class, new ValueSetter.LongSetter());
    typeToValueSetter.put(Float.class, new ValueSetter.FloatSetter());
    typeToValueSetter.put(Double.class, new ValueSetter.DoubleSetter());
  }
  
  @SuppressWarnings("unchecked")
  public static <T extends Number> T setValue(Class<T> type, double value)
  {
    return (T)typeToValueSetter.get(type).setValue(value);
  }
}
