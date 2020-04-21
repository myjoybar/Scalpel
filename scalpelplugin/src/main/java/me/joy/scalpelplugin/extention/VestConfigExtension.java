package me.joy.scalpelplugin.extention;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 2020/4/21
 */
public class VestConfigExtension {

  private List<String> vestModules = new ArrayList<>();
  private List<String> excludeFiles = new ArrayList<>();


  public void vestModules(List<String> vestModules) {
    this.vestModules = vestModules;
  }


  public List<String> vestModules() {
    return this.vestModules;
  }


  public void excludeFiles(List<String> excludeFiles) {
    this.excludeFiles = excludeFiles;
  }


  public List<String> excludeFiles() {
    return this.excludeFiles;
  }


}
