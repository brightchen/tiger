package cg.dimension.compute;

import java.util.Calendar;

import com.google.common.collect.Lists;

import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.property.BeanPropertyValueGenerator;

/**
 * @author bright
 *
 * @param <B>
 * @param <V>
 */
public abstract class AbstractTimeSlideAggregator<B, V extends Number> extends CompositeAggregator<B, V>
{
  public static enum TimeBaseType
  {
    CurrentTime,
    SpecificTime
  }
  
  protected String name;
  protected long slideStep;     //for example 1 second
  protected long timePeriod;    //for example 1 hour
  protected TimeBaseType timeBaseType;
  protected long baseTime;      //this field is be managed by this class if timeBaseType is CurrentTime
  
  /**
   * should the time when create time bucket, for example, if slideStep is 1 hour, the bucket should be [8:00, 9:00] 
   * instead of [8:10, 9:10]
   */
  protected boolean alignTime = true;  
  
  public AbstractTimeSlideAggregator(){}
  
  //slide base on current time
  public AbstractTimeSlideAggregator(long timePeriod, long slideStep)
  {
    init(timePeriod, slideStep);
  }
  
  //slide base on specific time
  public AbstractTimeSlideAggregator(long timePeriod, long slideStep, long baseTime)
  {
    init(timePeriod, slideStep, baseTime);
  }
  
  public void init(long timePeriod, long slideStep)
  {
    this.timePeriod = timePeriod;
    this.slideStep = slideStep;
    timeBaseType = TimeBaseType.CurrentTime;
  }
  
  public void init(long timePeriod, long slideStep, long baseTime)
  {
    this.timePeriod = timePeriod;
    this.slideStep = slideStep;
    this.baseTime = baseTime;
    timeBaseType = TimeBaseType.SpecificTime;
  }
  
  protected void createSubAggregators()
  {
    int aggregatorNum = (int)(timePeriod/slideStep + timePeriod%slideStep);
    long bucketTimeSpan = slideStep;
    if(TimeBaseType.CurrentTime == timeBaseType)
    {
      baseTime = Calendar.getInstance().getTimeInMillis();
    }
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
  

  /**
   * we can use the same logic as CompositeAggregator.
   * But it performance well.
   * we should locate the sub-aggregator and just delegate to it instead of waste time by delegate to other aggregators
   */
  @Override
  public void processBean(B bean)
  {
    //let the sub aggregator handle this bean
    for(Aggregator<B, V> subAggregator : subAggregators)
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

  public TimeBaseType getTimeBaseType()
  {
    return timeBaseType;
  }

  public void setTimeBaseType(TimeBaseType timeBaseType)
  {
    this.timeBaseType = timeBaseType;
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