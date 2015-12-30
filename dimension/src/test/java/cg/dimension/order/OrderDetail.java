package cg.dimension.order;

import java.util.List;

public class OrderDetail {
  public Customer customer;
  public List<ProductInfo> products;
  public OrderInfo orderInfo;
  
  public int getProductSize()
  {
    return products.size();
  }
}
