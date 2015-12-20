package cg.common;

import org.junit.Assert;
import org.junit.Test;

import cg.common.math.Calculator;

public class CalculatorTester
{
  @Test
  public void testAdd()
  {
    Assert.assertTrue( Calculator.add(new Short((short)7), new Short((short)3)) == 10 );
    Assert.assertTrue( Calculator.add(new Integer(1), new Integer(3)) == 4 );
    Assert.assertTrue( Calculator.add(new Float(2.0), new Float(3.0)) == 5.0 );
    
    Assert.assertTrue( Calculator.addTo(5L, new Short((short)7)) == 12 );
    Assert.assertTrue( Calculator.addTo(new Double(1), new Double(2.0)) == 3 );
  }

}
