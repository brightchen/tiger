package cg.codehaus.json;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Tester
{
  public static final String FIELD_TYPE = "type";
  public static final String FIELD_DATA = "data";
  public static final String FIELD_ID = "id";
  public static final String DataResultDimensional_TYPE = "dataResult";
  
  public static String[] messages = new String[]
    {
    };
  
  //the emtpy data tuple should be same, cache it.
  protected String emptyDataTuple;
  //id ==> list of data
  protected Map<String, List<JSONArray>> dataArrayMap = Maps.newHashMap();
  protected Map<String, JSONObject> jsonObjectMap = Maps.newHashMap();

  @Test
  public void test()
  {
    beginWindow(1);
    
    for(String message : messages)
      process(message);
    
    endWindow();
  }
  
  public void beginWindow(long windowId)
  {
    dataArrayMap.clear();
    jsonObjectMap.clear();
  }

  public void endWindow()
  {
    emitMergeResults();
  }

  public void process(String tuple)
  {
    JSONObject jo = null;

    try {
      jo = new JSONObject(tuple);
      if (jo.getString(FIELD_TYPE).equals(DataResultDimensional_TYPE)) {
        JSONArray dataArray = jo.getJSONArray(FIELD_DATA);
        String id = jo.getString(FIELD_ID);
        cacheData(id, jo, dataArray);
      } else {
        System.out.println(tuple);
      }
    } catch (JSONException ex) {
      throw new RuntimeException(ex);
    }
  }

  /**
   * @param id
   * @param dataArray
   */
  protected void cacheData(String id, JSONObject jo, JSONArray dataArray)
  {
    if(jsonObjectMap.get(id) == null)
      jsonObjectMap.put(id, jo);
    
    List<JSONArray> dataList = dataArrayMap.get(id);
    if(dataList == null)
    {
      dataList = Lists.newArrayList();
      dataArrayMap.put(id, dataList);
    }
    dataList.add(dataArray);
  }
  
  protected void emitMergeResults()
  {
    for(Map.Entry<String, JSONObject> jsonObjectEntry : jsonObjectMap.entrySet())
    {
      List<JSONArray> dataList = dataArrayMap.get(jsonObjectEntry.getKey());
      generateOutput(jsonObjectEntry.getValue(), dataList);
    }
  }
  
  protected String generateOutput(JSONObject jsonObject, List<JSONArray> dataList)
  {
    try {
      
      JSONArray dataArray = jsonObject.getJSONArray(FIELD_DATA);
      jsonObject.remove(FIELD_DATA);
      for(JSONArray data : dataList)
      {
      }
      return null;
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
}
