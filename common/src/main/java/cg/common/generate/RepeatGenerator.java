package cg.common.generate;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * RepeatGenerator is a generator which will repeat what bean generated.
 * 
 * @author bright
 *
 * @param <B>
 */
public class RepeatGenerator<B> implements BeanGenerator<B>
{
  protected BeanGenerator<B> embedGenerator;
  
  //how many beans generated before repeat.
  protected int batchSize = 1;
  //repeat how many times, 1 means duplicate once
  protected int repeatSize = 1;
  
  protected List<B> cachedBean = Lists.newArrayList();
  protected int repeatIndex = 0;
  protected int beanIndex = 0;
  
  @Override
  public B generate()
  {
    if (repeatIndex == 0) {
      //generate initial
      if( beanIndex++ < batchSize ) {
        B bean = embedGenerator.generate();
        if (repeatSize > 1) {
          cachedBean.add(bean);
        }
        return bean;
      }
      
      //beanIndex == batchSize;
      repeatIndex = 1;  //start first repeat
      beanIndex = 0;
    }
   
    //repeat
    B bean = cachedBean.get(beanIndex++);
    if(beanIndex == cachedBean.size())
    {
      beanIndex = 0;
      if(++repeatIndex == repeatSize+1 )
      {
        //prepare for next repeat.
        cachedBean.clear();
        repeatIndex = 0;
      }
    }
    return bean;
  }

  public RepeatGenerator<B> withEmbedGenerator(BeanGenerator<B> embedGenerator)
  {
    this.setEmbedGenerator(embedGenerator);
    return this;
  }
  
  public RepeatGenerator<B> withBatchSize(int batchSize)
  {
    this.setBatchSize(batchSize);
    return this;
  }
  
  public RepeatGenerator<B> withRepeatSize(int repeatSize)
  {
    this.setRepeatSize(repeatSize);
    return this;
  }
  
  public BeanGenerator<B> getEmbedGenerator()
  {
    return embedGenerator;
  }

  public void setEmbedGenerator(BeanGenerator<B> embedGenerator)
  {
    this.embedGenerator = embedGenerator;
  }

  public int getBatchSize()
  {
    return batchSize;
  }

  public void setBatchSize(int batchSize)
  {
    this.batchSize = batchSize;
  }

  public int getRepeatSize()
  {
    return repeatSize;
  }

  public void setRepeatSize(int repeatSize)
  {
    this.repeatSize = repeatSize;
  }

  
}
