package cg.dimension;

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cg.dimension.compute.SimpleAggregator;
import cg.dimension.compute.Aggregator;
import cg.dimension.compute.DefaultTimeSlideAggregator;
import cg.dimension.compute.AggregatorsAssembler;
import cg.dimension.model.aggregate.AggregateType;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.criteria.PropertyCriteria;
import cg.dimension.model.matcher.EqualsMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;

public class DimensionTester
{
  private static final Logger logger = LoggerFactory.getLogger(DimensionTester.class);
  
  protected int COUNT = 1000000;
  
  @Test
  public void test()
  {
    AggregatorsAssembler<TestRecord> computation = new AggregatorsAssembler<TestRecord>();
    
    //1. sum of salary of age equals 30;
    {
      BeanPropertyValueGenerator valueGenerator = new BeanPropertyValueGenerator(TestRecord.class, "age", Integer.class);
      PropertyCriteria criteria = new PropertyCriteria(valueGenerator, new EqualsMatcher<Integer>(30));
      Aggregator aggregator = computation.addAggregator("1", criteria, new BeanPropertyValueGenerator(TestRecord.class, "salary", Double.class), Double.class, AggregateType.SUM );
    }
    
    //2. average age of the records of last 1 minute ( slide by 1 second );
    {
      BeanPropertyValueGenerator valueGenerator = new BeanPropertyValueGenerator(TestRecord.class, "createdTime", Long.class);
      DefaultTimeSlideAggregator<TestRecord> aggregator = new DefaultTimeSlideAggregator<TestRecord>(valueGenerator, 
          new IncrementalAggregateSum(Long.class));
      //dynamic time matcher?
      //PropertyCriteria criteria = new PropertyCriteria(valueGenerator, 30, new EqualsMatcher());
      //computation.addAggregator("2",  );
    }
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    for(int i=0; i<COUNT; ++i)
      computation.processRecord(new TestRecord(i));
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    logger.info("records {}; value is {}; took {} milliseconds", COUNT, computation.getValue("1"), endTime - beginTime);
  }
  
  
}
