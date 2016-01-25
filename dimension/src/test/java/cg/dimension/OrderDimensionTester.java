package cg.dimension;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

import cg.common.general.Pair;
import cg.common.generate.Range;
import cg.dimension.group.DefaultBeanMatcherDynamicGroup;
import cg.dimension.group.DefaultGroupAggregate;
import cg.dimension.group.Group;
import cg.dimension.group.GroupAggregateByValueEqualsMatcher;
import cg.dimension.group.SimpleGroupAggregate;
import cg.dimension.groupcoll.MapAutoGenerateGroupCollection;
import cg.dimension.groupcoll.SimpleMapAutoGenerateGroupCollection;
import cg.dimension.handler.BeanAggregateHandler;
import cg.dimension.model.aggregate.IncrementalAggregateSum;
import cg.dimension.model.matcher.EqualsMatcher;
import cg.dimension.model.matcher.MatcherTimeRangeMapper;
import cg.dimension.model.matcher.MatcherTimeRangeMapper.TimeRangePolicy;
import cg.dimension.model.matcher.RangeMatcher;
import cg.dimension.model.matcher.TypicalValueMatcherSpec;
import cg.dimension.model.property.BeanMultiplePropertyValueGenerator;
import cg.dimension.model.property.BeanPropertyValueGenerator;
import cg.dimension.model.property.DefaultBeanMatcher;
import cg.dimension.order.Country;
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
  private static boolean wantVerify = true;
  protected int COUNT = 200000;
  

  @Test
  public void testSumOfProductsGroupByZip()
  {
    //DefaultAggregatorChain<OrderDetail> chain = new DefaultAggregatorChain<>();
    
    //- sum of products group by zip
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    IncrementalAggregateSum<Integer> aggregateSum = new IncrementalAggregateSum<Integer>();
    GroupAggregateByValueEqualsMatcher<OrderDetail, String, Integer> zipGroupAggregate = GroupAggregateByValueEqualsMatcher.<OrderDetail, String, Integer>create();
    zipGroupAggregate.withMatchPropertyValueGenerator(zipGenerator);
    zipGroupAggregate.withAggregatePropertyValueGenerator(productSizeGenerator);
    zipGroupAggregate.withAggregate(aggregateSum);
   
    
    //SimpleAutoGenerateGroupChain<OrderDetail, String> gc = new SimpleAutoGenerateGroupChain<>();
    SimpleMapAutoGenerateGroupCollection<OrderDetail, String> gc = new SimpleMapAutoGenerateGroupCollection<>();
    gc.setValueGenerator(zipGenerator);
    gc.setTemplate(zipGroupAggregate);
    
    Map<String, Integer> expectZipToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      gc.put(od);
      if(wantVerify)
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
    if(wantVerify)
    {
      Map<String, Integer> actualProductToSum = Maps.newHashMap();
      Collection<Group<OrderDetail, String>> groups = gc.getGroups();
      for(Group<OrderDetail, String> group : groups)
      {
        GroupAggregateByValueEqualsMatcher<OrderDetail, String, Integer> realGroup = (GroupAggregateByValueEqualsMatcher<OrderDetail, String, Integer>)group;
        
        @SuppressWarnings("unchecked")
        String zip = ((EqualsMatcher<String, String>)realGroup.getMatcher()).getExpectedValue();
        
        int count = realGroup.getAggregate().getValue();
        
        actualProductToSum.put(zip, count);
      }
      
      verifyResult(actualProductToSum, expectZipToProductSum);
    }
  }
  
  
  
  /**
   * use optimized way to implement sum of products group by zip
   */
  @Test
  public void testSumOfProductsGroupByZipOptimized()
  {
    SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer> groupAggregate = new SimpleGroupAggregate<>();
    groupAggregate.withAggregate(new IncrementalAggregateSum<Integer>());
    groupAggregate.withMatcherTemplate(new EqualsMatcher<String,String>());
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    groupAggregate.withMatchPropertyValueGenerator(zipGenerator);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    groupAggregate.withAggregatePropertyValueGenerator(productSizeGenerator);
    
    //SimpleAutoGenerateGroupChain<OrderDetail, String> groupChain = new SimpleAutoGenerateGroupChain<>();
    SimpleMapAutoGenerateGroupCollection<OrderDetail, String> groupChain = new SimpleMapAutoGenerateGroupCollection<>();
    groupChain.setValueGenerator(zipGenerator);
    groupChain.setTemplate(groupAggregate);
    
    Map<String, Integer> expectZipToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      //logger.debug("(zip, product-size) : ({}, {})", od.customer.zip, od.products.size());
      groupChain.put(od);
      if(wantVerify)
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
    
    if(wantVerify)
    {
      //verify
      Map<String, Integer> actualProductToSum = Maps.newHashMap();
      Collection<Group<OrderDetail, String>> groups = groupChain.getGroups();
      for(Group<OrderDetail, String> group : groups)
      {
        @SuppressWarnings("unchecked")
        SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer> realGroup = (SimpleGroupAggregate<EqualsMatcher<String,String>, OrderDetail, String, Integer>)group;
        
        @SuppressWarnings("unchecked")
        String zip = ((EqualsMatcher<String, String>)realGroup.getMatcher()).getExpectedValue();
        
        int count = realGroup.getAggregate().getValue();
        
        actualProductToSum.put(zip, count);
      }
      
      verifyResult(actualProductToSum, expectZipToProductSum);
    }
  }
  
  public <K, V> void verifyResult(Map<K, V> actualResult, Map<K, V> expectResult)
  {
    Assert.assertTrue("actual size: " + actualResult.size() + ", expected size: " + expectResult.size(), 
        actualResult.size() == expectResult.size());
    
    MapDifference<K, V> difference = Maps.difference(actualResult, expectResult);
    Assert.assertTrue(difference.toString(), difference.areEqual());
  }
  
  
  
  @Test
  public void testSumOfProductsGroupByZipAndCountry()
  {
    DefaultBeanMatcher<OrderDetail> beanMatcher = new DefaultBeanMatcher<>();
    beanMatcher.addMatcherInfo(OrderDetail.class, "customer.zip", String.class, new EqualsMatcher<String, String>());
    beanMatcher.addMatcherInfo(OrderDetail.class, "customer.country", Country.class, new EqualsMatcher<String, Country>());

    DefaultBeanMatcherDynamicGroup<DefaultBeanMatcher<OrderDetail>, OrderDetail, Integer, Map<String, ?>> countryZipGroup = new DefaultBeanMatcherDynamicGroup<>();
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    countryZipGroup.withBeanHandler(new BeanAggregateHandler<OrderDetail, Integer>()
                           .withAggregate(new IncrementalAggregateSum<Integer>())
                           .withAggregateValueGenerator(productSizeGenerator)).withMatchTemplate(beanMatcher);
    
    BeanPropertyValueGenerator<OrderDetail, Country> countryGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.country", Country.class);
    BeanPropertyValueGenerator<OrderDetail, String> zipGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "customer.zip", String.class);
    BeanMultiplePropertyValueGenerator<OrderDetail> zipCountryValueGenerator = new BeanMultiplePropertyValueGenerator<OrderDetail>()
                                                                     .addPropertyValueGenerator(countryGenerator)
                                                                     .addPropertyValueGenerator(zipGenerator);
//    SimpleAutoGenerateGroupChain<OrderDetail, Map<String, Object>> gc = new SimpleAutoGenerateGroupChain<>();
    SimpleMapAutoGenerateGroupCollection<OrderDetail, Map<String, ?>> gc = new SimpleMapAutoGenerateGroupCollection<>();
    gc.setValueGenerator(zipCountryValueGenerator);    
    gc.setTemplate(countryZipGroup);
    
    Map<String, Integer> expectCountryZipToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      //zipGroupAggregate.put(od);
      gc.put(od);
      
      if(wantVerify)
      {
        //generate actual result;
        String zip = od.customer.zip;
        String country = od.customer.country.name();
        int productSize = od.getProductSize();
        String country_zip = country + "_" + zip;
        Integer productSum = expectCountryZipToProductSum.get(country_zip);
        if(productSum == null)
        {
          expectCountryZipToProductSum.put(country_zip, productSize);
        }
        else
        {
          expectCountryZipToProductSum.put(country_zip, productSum+productSize);
        }
      }
    }
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    if(wantVerify)
    {
      //verify
      Map<String, Integer> actualProductToSum = Maps.newHashMap();
      Collection<Group<OrderDetail, Map<String, ?>>> groups = gc.getGroups();
      for(Group<OrderDetail, Map<String, ?>> group : groups)
      {
        DefaultBeanMatcherDynamicGroup<DefaultBeanMatcher<OrderDetail>, OrderDetail, Integer, Map<String, ?>> realGroup = (DefaultBeanMatcherDynamicGroup<DefaultBeanMatcher<OrderDetail>, OrderDetail, Integer, Map<String, ?>>)group;
        
        DefaultBeanMatcher<OrderDetail> matcher = (DefaultBeanMatcher<OrderDetail>)realGroup.getMatcher();
        
        List<Pair<BeanPropertyValueGenerator<OrderDetail, Object>, TypicalValueMatcherSpec<?, Object, Object, Object>>> matcherInfos = matcher.getMatcherInfos();
        String country_zip = "";
        {
          Pair<BeanPropertyValueGenerator<OrderDetail, Object>, TypicalValueMatcherSpec<?, Object, Object, Object>> pair = matcherInfos.get(1);
          country_zip += ((EqualsMatcher)pair.getRight()).getExpectedValue();
        }
        {
          Pair<BeanPropertyValueGenerator<OrderDetail, Object>, TypicalValueMatcherSpec<?, Object, Object, Object>> pair = matcherInfos.get(0);
          country_zip += "_";
          country_zip += ((EqualsMatcher)pair.getRight()).getExpectedValue();
        }
        
        int count = (Integer)((BeanAggregateHandler)realGroup.getBeanHandler()).getAggregate().getValue();
        
        actualProductToSum.put(country_zip, count);
      }
      
      this.verifyResult(actualProductToSum, expectCountryZipToProductSum);
    }
  }
  
  
  @Test
  public void testSumOfProductsGroupByTimeRange()
  {
    DefaultGroupAggregate<RangeMatcher<Long>, Range<Long>, OrderDetail, Long, Integer> groupAggregate = new DefaultGroupAggregate<>();
    groupAggregate.withAggregate(new IncrementalAggregateSum<Integer>());
    groupAggregate.withMatcherTemplate(new RangeMatcher<Long>());
    final MatcherTimeRangeMapper timeRangeMapper = new MatcherTimeRangeMapper().withTimeRangePolicy(TimeRangePolicy.PER_SECOND);
    groupAggregate.withMatcherValueMapper(timeRangeMapper);
    BeanPropertyValueGenerator<OrderDetail, Long> timeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "orderInfo.orderTime", Long.class);
    groupAggregate.withMatchPropertyValueGenerator(timeGenerator);
    BeanPropertyValueGenerator<OrderDetail, Integer> productSizeGenerator = new BeanPropertyValueGenerator<>(OrderDetail.class, "productSize", Integer.class);
    groupAggregate.withAggregatePropertyValueGenerator(productSizeGenerator);
    
//    SimpleAutoGenerateGroupChain<OrderDetail, Range<Long>> groupChain = new SimpleAutoGenerateGroupChain<>();
    MapAutoGenerateGroupCollection<OrderDetail, Long, Range<Long>> groupChain = new MapAutoGenerateGroupCollection<>();
    groupChain.setValueGenerator(timeGenerator);
    groupChain.setMatcherValueMapper(timeRangeMapper);
    groupChain.setTemplate(groupAggregate);
    
    Map<Range<Long>, Integer> expectTimeRangeToProductSum = Maps.newHashMap();
    
    long beginTime = Calendar.getInstance().getTimeInMillis();
    OrderDetailGenerator generator = new OrderDetailGenerator();
    for(int i=0; i<COUNT; ++i)
    {
      OrderDetail od = generator.generate();
      //logger.debug("(zip, product-size) : ({}, {})", od.customer.zip, od.products.size());
      groupChain.put(od);
      
      if(wantVerify)
      {
        //generate actual result;
        long orderTime = od.orderInfo.orderTime;
        int productSize = od.getProductSize();
        
        long startValue = orderTime - orderTime % (1000);
        Range<Long> timeRange = new Range<Long>(startValue, startValue+1000);
        
        Integer productSum = expectTimeRangeToProductSum.get(timeRange);
        if(productSum == null)
        {
          expectTimeRangeToProductSum.put(timeRange, productSize);
        }
        else
        {
          expectTimeRangeToProductSum.put(timeRange, productSum+productSize);
        }
      }
    }
    long endTime = Calendar.getInstance().getTimeInMillis();
    
    if(wantVerify)
    {
      //verify
      Map<Range<Long>, Integer> actualProductToSum = Maps.newHashMap();
      Collection<Group<OrderDetail, Range<Long>>> groups = groupChain.getGroups();
      for(Group<OrderDetail, Range<Long>> group : groups)
      {
        @SuppressWarnings("unchecked")
        DefaultGroupAggregate<RangeMatcher<Long>, Range<Long>, OrderDetail, Long, Integer> realGroupAggregate = (DefaultGroupAggregate<RangeMatcher<Long>, Range<Long>, OrderDetail, Long, Integer>)group;
        
        @SuppressWarnings("unchecked")
        Range<Long> timeRange = ((RangeMatcher<Long>)realGroupAggregate.getMatcher()).getRange();
        
        int count = realGroupAggregate.getAggregate().getValue();
        
        actualProductToSum.put(timeRange, count);
      }
      
      verifyResult(actualProductToSum, expectTimeRangeToProductSum);
    }
  }
}
