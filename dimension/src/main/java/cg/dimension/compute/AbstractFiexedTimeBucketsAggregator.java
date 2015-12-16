package cg.dimension.compute;

import java.util.Calendar;

import com.google.common.collect.Lists;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * This class used when the time period (begin time and end time) is fixed.
 * @author bright
 *
 * @param <B>
 * @param <AV>
 */
public abstract class AbstractFiexedTimeBucketsAggregator<B, MV, AV extends Number> extends CompositeAggregator<B, MV, AV>
{
  public static final int DEFAULT_SLIDE_STEP = 1000;  //default is 1 second
  protected String name;
  protected long slideStep = DEFAULT_SLIDE_STEP;     //for example 1 second
  protected long timePeriod;    //for example 1 hour
  protected long baseTime;      //this field is be managed by this class if timeBaseType is CurrentTime
  
  /**
   * should the time when create time bucket, for example, if slideStep is 1 hour, the bucket should be [8:00, 9:00] 
   * instead of [8:10, 9:10]
   */
  protected boolean alignTime = true;  
  
  public AbstractFiexedTimeBucketsAggregator(){}
  
  //slide base on current time
  public AbstractFiexedTimeBucketsAggregator(long timePeriod, long slideStep)
  {
    init(timePeriod, slideStep);
  }
  
  //slide base on specific time
  public AbstractFiexedTimeBucketsAggregator(long timePeriod, long slideStep, long baseTime)
  {
    init(timePeriod, slideStep, baseTime);
  }
  
  public void init(long timePeriod, long slideStep)
  {
    this.timePeriod = timePeriod;
    this.slideStep = slideStep;
  }
  
  public void init(long timePeriod, long slideStep, long baseTime)
  {
    this.timePeriod = timePeriod;
    this.slideStep = slideStep;
    this.baseTime = baseTime;
  }
  
  /**
   * delay call this method until it required to give the chance for the client code to set the parameters
   */
  protected void createSubAggregators()
  {
    int aggregatorNum = (int)(timePeriod/slideStep + timePeriod%slideStep);
    long bucketTimeSpan = slideStep;
    baseTime = Calendar.getInstance().getTimeInMillis();
    if(alignTime)
    {
      baseTime -= baseTime%slideStep;
    }
    
    long bucketBeginTime = baseTime;
    subAggregators = Lists.newArrayListWithCapacity(aggregatorNum);
    for(int index=0; index<aggregatorNum; ++index)
    {
      subAggregators.add(createSubAggregator(bucketBeginTime, bucketTimeSpan, index));
      bucketBeginTime += bucketTimeSpan;
    }
    
  }
  
  public void validateArguments() throws IllegalArgumentException
  {
    if(timePeriod <= 0)
      throw new IllegalArgumentException("timePeriod should not less or equal zero");
    if(slideStep <= 0)
      throw new IllegalArgumentException("slideStep should not less or equal zero");
  }

  /**
   * TODO: increase the performance
   * we can use the same logic as CompositeAggregator.
   * But it performance well.
   * we should locate the sub-aggregator and just delegate to it instead of waste time by delegate to other aggregators
   */
  @Override
  public void processBean(B bean)
  {
    validateArguments();
    if(subAggregators.isEmpty())
    {
      createSubAggregators();
    }
    
    //let the sub aggregator handle this bean
    for(Aggregator<B, MV, AV> subAggregator : subAggregators)
    {
      subAggregator.processBean(bean);
    }
  }
  
  protected abstract Aggregator createSubAggregator(long bucketBeginTime, long bucketTimeSpan, int index);

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public long getSlideStep()
  {
    return slideStep;
  }

  public void setSlideStep(long slideStep)
  {
    this.slideStep = slideStep;
  }

  public long getTimePeriod()
  {
    return timePeriod;
  }

  public void setTimePeriod(long timePeriod)
  {
    this.timePeriod = timePeriod;
  }

  public long getBaseTime()
  {
    return baseTime;
  }

  public void setBaseTime(long baseTime)
  {
    this.baseTime = baseTime;
  }

  public boolean isAlignTime()
  {
    return alignTime;
  }

  public void setAlignTime(boolean alignTime)
  {
    this.alignTime = alignTime;
  }
  
}
