package cg.dimension;

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cg.dimension.compute.SimpleAggregator;
import cg.dimension.compute.Aggregator;
import cg.dimension.compute.DefaultFiexedTimeBucketsAggregator;
import cg.dimension.compute.AggregatorsAssembler;
import cg.dimension.compute.DefaultAggregatorChain;
import cg.dimension.model.aggregate.Aggregate;
import cg.dimension.model.aggregate.AggregateType;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.matcher.EqualsMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class DimensionTester
{
  private static final Logger logger = LoggerFactory.getLogger(DimensionTester.class);
  private static final int SLEEP_TIME = 0;
  
  protected int COUNT = 10;
  
  @Test
  public void test()
  {
    DefaultAggregatorChain<TestRecord> chain = new DefaultAggregatorChain<TestRecord>();
    
    //1. sum of salary of age equals 30;
    {
      BeanPropertyValueGenerator<TestRecord, Integer> valueGenerator = new BeanPropertyValueGenerator<TestRecord, Integer>(TestRecord.class, "age", Integer.class);
      PropertyCriteria<TestRecord, Integer> criteria = new PropertyCriteria<TestRecord, Integer>(valueGenerator, new EqualsMatcher<Integer, Integer>(30));
      Aggregator aggregator = new SimpleAggregator("1", criteria, 
          new BeanPropertyValueGenerator(TestRecord.class, "salary", Double.class), new IncrementalAggregateSum(Double.class));
      //chain.addAggregator(aggregator);
    }
    
    //2. sum of age of the records of last 10 second ( slide by 1 second );
    {
      //TODO: test this case
      BeanPropertyValueGenerator<TestRecord, Long> timeGenerator = new BeanPropertyValueGenerator<>(TestRecord.class, "createdTime", Long.class);
      BeanPropertyValueGenerator<TestRecord, Integer> ageGenerator = new BeanPropertyValueGenerator<>(TestRecord.class, "age", Integer.class);
      DefaultFiexedTimeBucketsAggregator<TestRecord, Integer> aggregator = new DefaultFiexedTimeBucketsAggregator<>(timeGenerator, ageGenerator,
          new IncrementalAggregateSum<Integer>(Integer.class));
      aggregator.setName("2");
      aggregator.setValueType(Integer.class);
      aggregator.setTimePeriod(10000);
      aggregator.setBaseTime(Calendar.getInstance().getTimeInMillis());
      //dynamic time matcher?
      chain.addAggregator(aggregator);
    }
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    for(int i=0; i<COUNT; ++i)
    {
      TestRecord tr = new TestRecord(i);
      tr.setCreatedTime(beginTime);
      chain.processRecord(tr);
      
      if(SLEEP_TIME > 0)
      {
        try
        {
          Thread.sleep(SLEEP_TIME);
        }
        catch(Exception e){}
      }
    }
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    //logger.info("records {}; value is {}; took {} milliseconds", COUNT, chain.getValue("1"), endTime - beginTime);
    logger.info("records {}; value is {}; took {} milliseconds", COUNT, chain.getValue("2"), endTime - beginTime);
  }
  
  
}
