package cg.test;

import java.util.Random;

public class RandomStringGenerator {
  
  public static final int charRandomSeed = 456567;
  public static final int lengthRandomSeed = 6786758;
  
  public static final int maxLength = 128;
  protected static final Random charRandom = new Random(charRandomSeed);
  protected static final Random lengthRandom = new Random(lengthRandomSeed);
  
  
  public static char getChar()
  {
    //generate ascii (32,125)
    return (char)(charRandom.nextInt(94)+32);
  }
  
  public static String getString()
  {
    final int length = lengthRandom.nextInt(maxLength)+1;
    char[] chars = new char[length];
    for(int i=0; i<length; ++i)
    {
      chars[i] = getChar();
    }
    return String.valueOf(chars);
  }
}
