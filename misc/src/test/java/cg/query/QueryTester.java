package cg.query;

import java.io.IOException;
import java.util.Calendar;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cg.hbase.HbaseClientUtil;
import cg.test.TestTuple1;

public class QueryTester {
  private static final Logger logger = LoggerFactory.getLogger(QueryTester.class);
  
  protected String zookeeperQuorum = "localhost";
  protected int zookeeperClientPort = 2181;
  
  protected final String tableName = "querytest";
  
  protected String principal;
  protected String keytabPath;
  // Default interval 30 min
  protected long reloginCheckInterval = 30 * 60 * 1000;
  protected transient Thread loginRenewer;
  private volatile transient boolean doRelogin;
  
  protected HTable table;
  protected Configuration conf;
  
  protected boolean cleanTable = true;
  protected int rowSize = 100000;
  
  @Before
  public void prepareData() throws Exception
  {
    connect();
    
    HBaseAdmin admin = HbaseClientUtil.createAdmin(conf);
    boolean createTable = false;
    if(admin.isTableAvailable(tableName))
    {
      if(cleanTable)
      {
        HbaseClientUtil.deleteTable(admin, tableName);
        createTable = true;
      }
    }
    else
      createTable = true;
    
    if(createTable)
      HbaseClientUtil.createTable(admin, tableName, TestTuple1.getFieldName());
    
    table = new HTable(conf, tableName);
    table.setAutoFlushTo(false);
    
    logger.info("Start to write data.");
    long startTime = Calendar.getInstance().getTimeInMillis();

    for( int i = 0; i < rowSize; ++i )
    {
      TestTuple1 tuple = TestTuple1.createRandomly(i);
      Put put = new Put(Bytes.toBytes(tuple.getRow()));
      put.add(Bytes.toBytes("name"), Bytes.toBytes("name"), Bytes.toBytes(tuple.getName()));
      put.add(Bytes.toBytes("age"), Bytes.toBytes("age"), Bytes.toBytes(tuple.getAge()));
      put.add(Bytes.toBytes("address"), Bytes.toBytes("address"), Bytes.toBytes(tuple.getAddress()));
      put.add(Bytes.toBytes("desc"), Bytes.toBytes("desc"), Bytes.toBytes(tuple.getDesc()));
      table.put(put);
      
      if(i>0 && i%1000==0)
        table.flushCommits();
    }
    table.flushCommits();
    
    long spentTime = Calendar.getInstance().getTimeInMillis() - startTime;
    logger.info("Spent {} milli-second to write {} rows.", spentTime, rowSize);
  }
  
  protected void connect() throws IOException {
    if ((principal != null) && (keytabPath != null)) {
      String lprincipal = evaluateProperty(principal);
      String lkeytabPath = evaluateProperty(keytabPath);
      UserGroupInformation.loginUserFromKeytab(lprincipal, lkeytabPath);
      doRelogin = true;
      loginRenewer = new Thread(new Runnable()
      {
        @Override
        public void run()
        {
          logger.debug("Renewer starting");
          try {
            while (doRelogin) {
              Thread.sleep(reloginCheckInterval);
              try {
                UserGroupInformation.getLoginUser().checkTGTAndReloginFromKeytab();
              } catch (IOException e) {
                logger.error("Error trying to relogin from keytab", e);
              }
            }
          } catch (InterruptedException e) {
            if (doRelogin) {
              logger.warn("Renewer interrupted... stopping");
            }
          }
          logger.debug("Renewer ending");
        }
      });
      loginRenewer.start();
    }
    conf = HBaseConfiguration.create();
    // The default configuration is loaded from resources in classpath, the following parameters can be optionally set
    // to override defaults
    if (zookeeperQuorum != null) {
      conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
    }
    if (zookeeperClientPort != 0) {
      conf.set("hbase.zookeeper.property.clientPort", "" + zookeeperClientPort);
    }
    table = new HTable(conf, tableName);
    table.setAutoFlushTo(false);

  }
  
  public static final String USER_NAME_SPECIFIER = "%USER_NAME%";
  private String evaluateProperty(String property) throws IOException
  {
    if (property.contains(USER_NAME_SPECIFIER)) {
     property = property.replaceAll(USER_NAME_SPECIFIER, UserGroupInformation.getLoginUser().getShortUserName());
    }
    return property;
  }
  
  @Test
  public void test()
  {
    
  }
}
