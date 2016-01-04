package cg.dimension.order;

import java.util.Calendar;

import cg.common.generate.BeanGenerator;
import cg.common.generate.EnumRandomGenerator;
import cg.common.generate.RandomHolder;
import cg.common.generate.Range;

public class OrderInfoRandomGenerator implements BeanGenerator<OrderInfo>
{
  public final static OrderInfoRandomGenerator todayOrderInfoRandomGeneratorInst;
  
  static
  {
    Calendar todayBegin = Calendar.getInstance();
    todayBegin.set(Calendar.HOUR_OF_DAY, 0);
    todayBegin.set(Calendar.MINUTE, 0);
    todayBegin.set(Calendar.SECOND, 0);
    todayBegin.set(Calendar.MILLISECOND, 0);
    
    Range<Calendar> rangeToday = new Range<Calendar>(todayBegin, Calendar.getInstance());
    todayOrderInfoRandomGeneratorInst = new OrderInfoRandomGenerator(rangeToday);
  }
  
  protected Range<Calendar> orderTimeRange;
  protected int orderTimeLength;
  protected EnumRandomGenerator<PayMethod> payMethodGenerator = new EnumRandomGenerator<>(PayMethod.class);
  

  public OrderInfoRandomGenerator(Range<Calendar> orderTimeRange)
  {
    setOrderTimeRange(orderTimeRange);
  }
  
  @Override
  public OrderInfo generate()
  {
    OrderInfo oi = new OrderInfo();
    oi.orderTime = orderTimeRange.from;
    oi.orderTime.add(Calendar.MILLISECOND, RandomHolder.random.nextInt(orderTimeLength));
    oi.payMethod = payMethodGenerator.generate();
    return oi;
  }

  public void setOrderTimeRange(Range<Calendar> orderTimeRange)
  {
    this.orderTimeRange = orderTimeRange;
    orderTimeLength = (int)(orderTimeRange.to.getTimeInMillis() - orderTimeRange.from.getTimeInMillis());
  }
}
