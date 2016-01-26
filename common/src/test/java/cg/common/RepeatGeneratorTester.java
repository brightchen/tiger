package cg.common;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cg.common.generate.CharRandomGenerator;
import cg.common.generate.RepeatGenerator;

public class RepeatGeneratorTester
{
  private static final Logger logger = LoggerFactory.getLogger(RepeatGeneratorTester.class);
  
  @Test
  public void test()
  {
    final int totalSize = 1200;
    final int batchSize = 10;
    final int repeatSize = 2;
    CharRandomGenerator embedGenerator = CharRandomGenerator.digitCharGenerator;
    RepeatGenerator<Character> generator = new RepeatGenerator()
        .withEmbedGenerator(embedGenerator)
        .withBatchSize(batchSize)
        .withRepeatSize(repeatSize);
    
    char[] chars = new char[totalSize];
    for(int i=0; i<totalSize; ++i)
    {
      chars[i] = generator.generate();
      System.out.print(chars[i]);
      System.out.print(',');
    }
    System.out.println();
    logger.info("chars: ", chars);
    
    
    //verify;
    if(repeatSize <= 0 || totalSize < batchSize)
      return;
    
    int start = batchSize;
    int index = batchSize;
    int repeatedCount = 0;
    for(; index < totalSize; ++index)
    {
      Assert.assertTrue("[" + index + "] != [" + (index-batchSize) + "]", chars[index] == chars[index - batchSize]);
      //reach the last item of the repeating
      if((index+1)%batchSize == 0)
      {
        ++repeatedCount;
        if(repeatedCount == repeatSize)
        {
          //prepare for next repeat
          repeatedCount = 0;
          index += batchSize;
          
        }
      }
    }
  }
}
