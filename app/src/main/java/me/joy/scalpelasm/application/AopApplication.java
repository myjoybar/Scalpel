package me.joy.scalpelasm.application;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import me.joy.scalpel.helper.viewclick.TrackConfigData;
import me.joy.scalpel.helper.viewclick.TrackConfigManager;


/**
 * Created by joybar on 14/04/2018.
 */

public class AopApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initTrackConfig();
    //ScalpelManager.setScalpelDelegateService(new ScalpelExecutorDelegate());

  }


  private void initTrackConfig() {
    TrackConfigManager.intConfig(getTrackConfigData());
  }

  private static List<TrackConfigData> getTrackConfigData() {
    String jsonStr = "[{\"className\":\"me.joy.scalpelasm.MainActivityPlugin\",\"resourceEntryNames\":[\"btn_test1\"],\"methodNames\":[\"businessTrack1\"]}]";
    List<TrackConfigData> trackConfigDataList = getObjectList(jsonStr, TrackConfigData.class);
    return trackConfigDataList;
  }

  public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
    List<T> list = new ArrayList<T>();
    try {
      Gson gson = new Gson();
      JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
      for (JsonElement jsonElement : arry) {
        list.add(gson.fromJson(jsonElement, cls));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;

  }


}
