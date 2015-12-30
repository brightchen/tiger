package cg.common.generate;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;

public class EnumRandomGenerator<E extends Enum<E>> implements BeanGenerator<E>
{
  protected Class<E> enumType;
  //protected E[] enums;
  protected List<E> enumList;
  
  @SuppressWarnings("unchecked")
  public EnumRandomGenerator(Class<E> enumType)
  {
    this.enumType = enumType;
    EnumSet<E> enumSet = EnumSet.allOf(enumType);
    enumList = Lists.newArrayList();
    for(E item : enumSet)
    {
      enumList.add(item);
    }
    //enums = (E[])enumSet.toArray();
  }
  
  @Override
  public E generate()
  {
    return enumList.get(RandomHolder.random.nextInt(enumList.size()));
  }
}
