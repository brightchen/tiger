package cg.dimension;

import java.util.Calendar;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.group.GroupAggregateByEqualsMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;
import cg.dimension.order.OrderDetail;
import cg.dimension.order.OrderDetailGenerator;

/**
 * use a order analytics system as the test case. cases
 *   - for distribution/logistic: product info group by location( country and zip, month );
 *   - product info group by ( country&state, sex, age range( kids, young, middle, old )
 *        
 * @author bright
 *
 */
public class OrderDimensionTester
{
  private static final Logger logger = LoggerFactory.getLogger(OrderDimensionTester.class);
  private static final int SLEEP_TIME = 0;
  
  protected int COUNT = 10;
  
  @Test
  public void test()
  {
    //DefaultAggregatorChain<OrderDetail> chain = new DefaultAggregatorChain<>();
    
    //- sum of products group by zip
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    IncrementalAggregateSum<Integer> aggregateSum = new IncrementalAggregateSum<Integer>();
    GroupAggregateByEqualsMatcher<OrderDetail, String, Integer> zipGroupAggregate = GroupAggregateByEqualsMatcher.<OrderDetail, String, Integer>create();
    zipGroupAggregate.withMatchPropertyValueGenerator(zipGenerator);
    zipGroupAggregate.withAggregatePropertyValueGenerator(productSizeGenerator);
    zipGroupAggregate.withAggregate(aggregateSum);
   
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      zipGroupAggregate.put(od);
    }
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    logger.info("records {}; value is {}; took {} milliseconds", COUNT, zipGroupAggregate.getAggregate().getValue(), endTime - beginTime);
  }
  
  
}
