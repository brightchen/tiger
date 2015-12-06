package cg.janino;

import java.util.Map;

import com.google.common.collect.Maps;

public class Calculator
{
  
  /**************************************************
  public static final String adderTemplate = 
  "package cg.janino; \n" +
  "public class AdderTYPE \n" +
  "{ \n" +
  "  public static TYPE add( TYPE v1, TYPE v2) \n" +  
  "  { \n" +
  "    return v1+v2; \n" +
  "  } \n" +  
  "}\n";
  
  public static final  String addInteger = adderTemplate.replaceAll("TYPE", "Integer");
  public static final String addLong = adderTemplate.replaceAll("TYPE", "Long");
  public static final String addFloat = adderTemplate.replaceAll("TYPE", "Float");
  public static final String addDouble = adderTemplate.replaceAll("TYPE", "Double");
  
  private static SimpleCompiler compiler = new SimpleCompiler();
  
  
  static 
  {
    try {
      compiler.cook(addInteger + addLong + addFloat + addDouble);
      Class classAdderInteger = compiler.getClassLoader().loadClass("cg.janino.AdderInteger");
      Class classAdderLong = compiler.getClassLoader().loadClass("cg.janino.AdderLong");
      Class classAdderFloat = compiler.getClassLoader().loadClass("cg.janino.AdderFloat");
      Class classAdderDouble = compiler.getClassLoader().loadClass("cg.janino.AdderDouble");
      
      classAdderInteger.add( 1, 2);
      classAdderInteger
  } catch (Exception e) {
      throw new IllegalArgumentException(e.getMessage());
  }
  /**************************************************/
  
  public static Map<Class, Adder> typeToAdder = Maps.newHashMap();
  static
  {
    typeToAdder.put(Short.class, new Adder.ShortAdder());
    typeToAdder.put(Integer.class, new Adder.IntegerAdder());
    typeToAdder.put(Float.class, new Adder.FloatAdder());
    typeToAdder.put(Double.class, new Adder.DoubleAdder());
  }
  
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
  
  //(AddenType, ResultType) ==> class
  public static Map<ClassPair, AddTo> typePairToAddTo = Maps.newHashMap();
  static
  {
    ClassPair longLong = new ClassPair(Long.class, Long.class);
    typePairToAddTo.put(longLong, new AddTo.LongAddToLong());
    
    ClassPair integerLong = new ClassPair(Integer.class, Long.class);
    typePairToAddTo.put(integerLong, new AddTo.IntegerAddToLong());
    
    ClassPair shortLong = new ClassPair(Short.class, Long.class);
    typePairToAddTo.put(shortLong, new AddTo.ShortAddToLong());
    
    ClassPair floatFloat = new ClassPair(Float.class, Float.class);
    typePairToAddTo.put(floatFloat, new AddTo.FloatAddToFloat());
    
    ClassPair doubleDouble = new ClassPair(Double.class, Double.class);
    typePairToAddTo.put(doubleDouble, new AddTo.DoubleAddToDouble());
  }
  
  public static <T extends Number> T add(Class<T> type, T value1, T value2)
  {
    return (T)typeToAdder.get(type).add(value1, value2);
  }
  
  public static <T extends Number, R extends Number> R addTo(Class<R> resultType, R orgValue, Class<T> type, T value)
  {
    return (R)typePairToAddTo.get(new ClassPair(type, resultType)).addTo(orgValue, value);
  }
}
