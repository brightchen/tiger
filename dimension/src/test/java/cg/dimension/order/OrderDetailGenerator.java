package cg.dimension.order;

import cg.common.generate.BeanGenerator;
import cg.common.generate.BeanListGenerator;

public class OrderDetailGenerator implements BeanGenerator<OrderDetail>
{
  protected CustomerGenerator customerGenerator = new CustomerGenerator();
  protected OrderInfoRandomGenerator orderInfoGenerator = OrderInfoRandomGenerator.todayOrderInfoRandomGeneratorInst;
  protected BeanListGenerator<ProductInfo> productInfoListGenerator = BeanListGenerator.<ProductInfo>create().withBeanGenerator(new ProductInfoRandomGenerator());
  
  @Override
  public OrderDetail generate()
  {
    OrderDetail od = new OrderDetail();
    od.customer = customerGenerator.generate();
    od.orderInfo = orderInfoGenerator.generate();
    od.products = productInfoListGenerator.generate();
    return od;
  }
}
