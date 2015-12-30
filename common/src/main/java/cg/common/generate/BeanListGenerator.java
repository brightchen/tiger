package cg.common.generate;

import java.util.List;

import com.google.common.collect.Lists;

public class BeanListGenerator<B> implements BeanGenerator<List<B>>
{
  protected static final int DEFAULT_MIN_SIZE = 1;
  protected static final int DEFAULT_MAX_SIZE = 1000;
  
  protected BeanGenerator<B> beanGenerator;
  protected int minSize;
  protected int maxSize;
  
  public BeanListGenerator()
  {
    minSize = DEFAULT_MIN_SIZE;
    maxSize = DEFAULT_MAX_SIZE;
  }
  
  public static <B> BeanListGenerator<B> create()
  {
    return new BeanListGenerator<B>();
  }
  
  public BeanListGenerator<B> withExactSize(int exactSize)
  {
    this.minSize = exactSize;
    this.maxSize = exactSize;
    return this;
  }
  
  public BeanListGenerator<B> withSize(int minSize, int maxSize)
  {
    this.minSize = minSize;
    this.maxSize = maxSize;
    return this;
  }
  
  public BeanListGenerator<B> withBeanGenerator(BeanGenerator<B> beanGenerator)
  {
    this.beanGenerator = beanGenerator;
    return this;
  }
  
  @Override
  public List<B> generate()
  {
    int size = minSize + RandomHolder.random.nextInt(maxSize - minSize);
    List<B> beans = Lists.newArrayListWithExpectedSize(size);
    for(int i=0; i<size; ++i)
    {
      beans.add(beanGenerator.generate());
    }
    return beans;
  }

  public BeanGenerator<B> getBeanGenerator()
  {
    return beanGenerator;
  }

  public void setBeanGenerator(BeanGenerator<B> beanGenerator)
  {
    this.beanGenerator = beanGenerator;
  }
  
  
}

