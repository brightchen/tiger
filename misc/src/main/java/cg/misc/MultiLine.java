package cg.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MultiLine {
  public static void main(String[] argvs) {
    MultiLine ml = new MultiLine();
    ml.print();
  }

  public void print()
  {
    System.out.println(S(/*
         * This is a CRAZY " ' ' " multiline string with all
         * sorts of strange characters!
         */));
  }
  
  public String S() {
    StackTraceElement element = new RuntimeException().getStackTrace()[1];
    String name = element.getClassName().replace('.', '/') + ".java";
    StringBuilder sb = new StringBuilder();
    String line = null;
    //InputStream in = ClassLoader.getSystemResourceAsStream(name); 
    InputStream in = this.getClass().getResourceAsStream(name);   //Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    String s = convertStreamToString(in, element.getLineNumber());
    return s.substring(s.indexOf("/*") + 2, s.indexOf("*/"));
  }

  private String convertStreamToString(InputStream is, int lineNum) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line = null;
    int i = 1;
    try {
      while ((line = reader.readLine()) != null) {
        if (i++ >= lineNum) {
          sb.append(line + "\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return sb.toString();
  }
}
