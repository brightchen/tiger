package cg.janino;

import org.junit.Assert;
import org.junit.Test;


public class CalculatorTester
{
  @Test
  public void testAdd()
  {
    Assert.assertTrue( Calculator.add(Short.class, new Short((short)7), new Short((short)3)) == 10 );
    Assert.assertTrue( Calculator.add(Integer.class, new Integer(1), new Integer(3)) == 4 );
    Assert.assertTrue( Calculator.add(Float.class, new Float(2.0), new Float(3.0)) == 5.0 );
  }
  
  @Test
  public void testAddTo()
  {
    Assert.assertTrue( Calculator.addTo(Long.class, 5L, Short.class, new Short((short)7)) == 12 );
    Assert.assertTrue( Calculator.addTo(Double.class, new Double(1), Double.class, new Double(2.0)) == 3 );
  }
}
