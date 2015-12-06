package cg.hive.client;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

import java.sql.DriverManager;

/**
 * start hive server: hiveserver2
 * 
 * @author bright
 *
 */
public class HiveQueryTester {
  //private static String driverName = "org.apache.hadoop.hive.jdbc.HiveDriver";    //not valid any more
  private static String driverName = "org.apache.hive.jdbc.HiveDriver";
  
  //private final String url="jdbc:hive://localhost:10000/test1";   //old
  private final String url="jdbc:hive2://localhost:10000/test1";    //hive 2 url
  private final String tableName = "testHiveDriverTable";
  
  /**
  * @param args
  * @throws SQLException
  **/
  @Test
  public void test() throws SQLException {
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e){
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(1);
    }
    
    //input password here.
    Connection con = DriverManager.getConnection(url, "bright", "");    
    Statement stmt = con.createStatement();
    
    stmt.executeUpdate("drop table " + tableName);
    
    stmt.executeUpdate("create table " + tableName + " (key int, value string)");
    
    // show tables
    String sql = "show tables '" + tableName + "'";
    System.out.println("Running: " + sql);
    ResultSet res = stmt.executeQuery(sql);
    if (res.next()) {
      System.out.println(res.getString(1));
    }
    // describe table
    sql = "describe " + tableName;
    System.out.println("Running: " + sql);  
    res = stmt.executeQuery(sql);
    while (res.next()) {
      System.out.println(res.getString(1) + "\t" + res.getString(2));
    }
    // load data into table
    // NOTE: filepath has to be local to the hive server
    // NOTE: /tmp/test_hive_server.txt is a ctrl-A separated file with two fields per line
    String filepath = "/tmp/test_hive_server.txt";
    sql = "load data local inpath '" + filepath + "' into table " + tableName;
    System.out.println("Running: " + sql);
    res = stmt.executeQuery(sql);
    // select * query
    sql = "select * from " + tableName;
    System.out.println("Running: " + sql);
    res = stmt.executeQuery(sql);
    while (res.next()){
      System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
    }
    // regular hive query
    sql = "select count(1) from " + tableName;
    System.out.println("Running: " + sql);
    res = stmt.executeQuery(sql);
    while (res.next()){
      System.out.println(res.getString(1));
    }
  }
}