package cg.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

public class DateTester {
  private long time;

  // MM/DD/YYYY
  protected final SimpleDateFormat dayFormat = new SimpleDateFormat("MM/dd/yyyy");
  //hh:mm:ss, 
  protected final SimpleDateFormat timeInDayFormat = new SimpleDateFormat("HH:mm:ss");
  //
  protected final SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
  
  
  public String getDate()
  {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(time);
    return dayFormat.format(c.getTime());
  }

  public String getTimeInDay()
  {
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(time);
    return timeInDayFormat.format(c.getTime());
  }
  
  public void setTime(String timeInDay, String day) {
    Calendar c = Calendar.getInstance();
    try {
      c.setTime(timeFormat.parse(day + " " + timeInDay));
      time = c.getTimeInMillis();
    } catch (ParseException e) {
    }
  }
  
  @Test
  public void test()
  {
    time = Calendar.getInstance().getTimeInMillis();
    String day = getDate();
    String timeInDay = getTimeInDay();
    System.out.println("getDate:" + day);
    System.out.println("getTimeInDay:" + timeInDay );
    
    setTime(timeInDay, day);
    
    day = getDate();
    timeInDay = getTimeInDay();
    System.out.println("getDate:" + day);
    System.out.println("getTimeInDay:" + timeInDay );
    
  }
  
}
