package cg.dimension;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cg.dimension.group.DefaultGroupAggregate;
import cg.dimension.group.Group;
import cg.dimension.group.GroupAggregateByEqualsMatcher;
import cg.dimension.group.SimpleGroupAggregate;
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
    //testSumOfProductsGroupByZipAndCountry();
    testSumOfProductsGroupByZipOptimized();
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
  
  
  
  /**
   * use optimized way to implement sum of products group by zip
   */
  public void testSumOfProductsGroupByZipOptimized()
  {
    SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer> groupAggregate = new SimpleGroupAggregate<>();
    groupAggregate.withAggregate(new IncrementalAggregateSum<Integer>());
    groupAggregate.withMatcherTemplate(new EqualsMatcher<String,String>());
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    groupAggregate.withMatchPropertyValueGenerator(zipGenerator);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    groupAggregate.withAggregatePropertyValueGenerator(productSizeGenerator);
    
    SimpleAutoGenerateGroupChain<OrderDetail> groupChain = new SimpleAutoGenerateGroupChain<>();
    groupChain.setTemplate(groupAggregate);
    
    Map<String, Integer> expectZipToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      logger.debug("(zip, product-size) : ({}, {})", od.customer.zip, od.products.size());
      groupChain.put(od);
      
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
    
    //verify
    Map<String, Integer> actualProductToSum = Maps.newHashMap();
    Collection<Group<OrderDetail>> groups = groupChain.getGroups();
    for(Group<OrderDetail> group : groups)
    {
      @SuppressWarnings("unchecked")
      SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer> realGroup = (SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer>)group;
      
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
  
  
  
  public void testSumOfProductsGroupByZipAndCountry()
  {
    //DefaultAggregatorChain<OrderDetail> chain = new DefaultAggregatorChain<>();
    
    //- sum of products group by zip and country
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    IncrementalAggregateSum<Integer> aggregateSum = new IncrementalAggregateSum<Integer>();
    //TODO: should use add matcher
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
