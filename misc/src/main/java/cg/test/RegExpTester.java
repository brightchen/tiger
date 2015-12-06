package cg.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegExpTester
{
  private static final transient Logger logger = LoggerFactory.getLogger(RegExpTester.class);
      
  protected static final String TMP_PATTERN = "\\S+\\.tmp$";
  protected static final String NON_TMP_PATTERN = "\\S+\\.\\d+$";
  
  @Test
  public void test()
  {
    String[] entries = new String[]{"1.tmp", "sdfsdf.tmp", "111tmp", "11tmp22", ".tmp", "aaaa.1", "aaa.b1", "aaa.22"};
    
    Pattern p = Pattern.compile(NON_TMP_PATTERN);
    
    for(String entry : entries)
    {
      Matcher m = p.matcher(entry);
      logger.info("'{}' {} '{}'", entry, (m.matches() ? "matches" : "not match"), NON_TMP_PATTERN);
    }
  }
  
}
