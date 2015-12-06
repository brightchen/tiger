package cg.nio;

import java.io.IOException;

import org.junit.Test;

public class ServerSocketChannelTester {
  @Test
  public void test()
  {
    try {
      ServerSocketChannel sc = new ServerSocketChannel();
      sc.create(10025);
      
      Thread.sleep(1000000);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
