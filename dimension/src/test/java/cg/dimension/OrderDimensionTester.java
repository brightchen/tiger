package cg.dimension;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cg.dimension.group.Group;
import cg.dimension.group.GroupAggregateByEqualsMatcher;
import cg.dimension.groupcoll.GroupCollection;
import cg.dimension.groupcoll.SimpleAutoGenerateGroupChain;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.matcher.EqualsMatcher;
import cg.dimension.model.property.BeanPropertyValueGenerator;
import cg.dimension.order.OrderDetail;
import cg.dimension.order.OrderDetailGenerator;
import junit.framework.Assert;

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
  
  protected int COUNT = 1000;
  
  @Test
  public void test()
  {
    testSumOfProductsGroupByZip();
  }
  
  public void testSumOfProductsGroupByZip()
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
   
    
    SimpleAutoGenerateGroupChain<OrderDetail> gc = new SimpleAutoGenerateGroupChain<OrderDetail>();
    gc.setTemplate(zipGroupAggregate);
    
    Map<String, Integer> expectZipToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      //zipGroupAggregate.put(od);
      gc.put(od);
      
      {
        //generate actual result;
        String zip = od.customer.zip;
        int productSize = od.getProductSize();
        Integer productSum = expectZipToProductSum.get(zip);
        if(productSum == null)
        {
          expectZipToProductSum.put(zip, productSize);
        }
        else
        {
          expectZipToProductSum.put(zip, productSum+productSize);
        }
      }
    }
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    logger.info("records {}; value is {}; took {} milliseconds", COUNT, zipGroupAggregate.getAggregate().getValue(), endTime - beginTime);
    
    //verify
    Map<String, Integer> actualProductToSum = Maps.newHashMap();
    Collection<Group<OrderDetail>> groups = gc.getGroups();
    for(Group<OrderDetail> group : groups)
    {
      GroupAggregateByEqualsMatcher<OrderDetail, String, Integer> realGroup = (GroupAggregateByEqualsMatcher<OrderDetail, String, Integer>)group;
      
      @SuppressWarnings("unchecked")
      String zip = ((EqualsMatcher<String, String>)realGroup.getMatcher()).getExpectedValue();
      
      int count = realGroup.getAggregate().getValue();
      
      actualProductToSum.put(zip, count);
    }
    
    Assert.assertTrue("actual size: " + actualProductToSum.size() + ", expected size: " + expectZipToProductSum.size(), 
        actualProductToSum.size() == expectZipToProductSum.size());
    Set<String> zipSet = actualProductToSum.keySet();
    for(String zip : zipSet)
    {
      Assert.assertTrue("zip: " + zip + "; actual sum: " + actualProductToSum.get(zip) + "; exected sum: " + expectZipToProductSum.get(zip), 
          actualProductToSum.get(zip).equals(expectZipToProductSum.get(zip)) );
    }
  }
  
  
}
