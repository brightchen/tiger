package cg.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HbaseClientUtil {
  private static final Logger logger = LoggerFactory.getLogger(HbaseClientUtil.class);

  public static HBaseAdmin createAdmin(Configuration conf)
      throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
    return new HBaseAdmin(conf);
  }

  public static void createTable(HBaseAdmin admin, String tableName, String[] families) throws IOException {
    if (admin.isTableAvailable(tableName)) {
      final String message = "The table '" + tableName + "' already exists.";
      logger.warn(message);
      throw new IllegalArgumentException(message);
    }
    HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
    if (families != null && families.length > 0) {
      for (String family : families) {
        tableDescriptor.addFamily(new HColumnDescriptor(family));
      }
    }

    admin.createTable(tableDescriptor);

  }

  public static void deleteTable(HBaseAdmin admin, String tableName) throws IOException {
    admin.disableTable(tableName);
    admin.deleteTable(tableName);
  }
}
