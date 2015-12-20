package cg.common.generate;

import java.util.Set;

import com.google.common.collect.Sets;

public abstract class CachedBeanGenerator<B> implements BeanGenerator<B>
{
  public final static int DEFAULT_CAPACITY = 10000;
  
  //both use array to improve the performance.
  protected int cacheCapacity = DEFAULT_CAPACITY;
  protected Set<B> beans = Sets.newHashSet();
  protected B[] beanArray;
  
  @Override
  public B generate()
  {
    if(beans.size() < cacheCapacity)
    {
      B bean = generateBean();
      beans.add(bean);
      return bean;
    }
    else if(beans.size() == cacheCapacity)
    {
      if(beanArray == null)
      {
        beanArray = (B[])new Object[beans.size()]; 
        int i=0;
        for(B bean : beans)
        {
          beanArray[i++] = bean;
        }
      }
    }
    return getRandomCachedBean(cacheCapacity);
  }
  
  protected abstract B generateBean();
  
  
  /**
   * @return
   */
  protected B getRandomCachedBean(int capacity)
  {
    int index = RandomHolder.random.nextInt(capacity);
    return beanArray[index];
  }
}
