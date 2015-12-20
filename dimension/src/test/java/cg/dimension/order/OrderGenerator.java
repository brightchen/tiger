package cg.dimension.order;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cg.common.generate.BeanGenerator;

public class OrderGenerator implements BeanGenerator<OrderDetail>
{
  protected CustomerGenerator customerGenerator = new CustomerGenerator();
  @Override
  public OrderDetail generate()
  {
    OrderDetail od = new OrderDetail();
    od.customer = customerGenerator.generate();
    od.orderInfo = 
  }
}
