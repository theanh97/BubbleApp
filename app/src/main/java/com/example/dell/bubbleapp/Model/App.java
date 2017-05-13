package com.example.dell.bubbleapp.Model;

import android.graphics.Bitmap;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.Serializable;

/**
 * Created by DELL on 11/10/2016.
 */

public class App implements Serializable {
  private String appName, appPackage;
  private transient Bitmap icon;
  private transient MyImage myImage;

  public App(String appName, String appPackage, Bitmap icon, MyImage myImage, FrameLayout.LayoutParams layoutParams) {
    this.appName = appName;
    this.appPackage = appPackage;
    this.icon = icon;
    this.myImage = myImage;
    this.layoutParams = layoutParams;
  }

  public MyImage getMyImage() {
    return myImage;

  }

  public void setMyImage(MyImage myImage) {
    this.myImage = myImage;
  }

  public App(String appName, String appPackage, Bitmap icon, FrameLayout.LayoutParams layoutParams) {
    this.appName = appName;
    this.appPackage = appPackage;
    this.icon = icon;
    this.layoutParams = layoutParams;
  }

  private transient FrameLayout.LayoutParams layoutParams;

  public FrameLayout.LayoutParams getLayoutParams() {
    return layoutParams;
  }

  public void setLayoutParams(FrameLayout.LayoutParams layoutParams) {
    this.layoutParams = layoutParams;
  }



  public App(String appName, String appPackage, Bitmap icon) {
    this.appName = appName;
    this.appPackage = appPackage;
    this.setIcon(icon);
  }

  public String getAppName() {

    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppPackage() {
    return appPackage;
  }

  public void setAppPackage(String appPackage) {
    this.appPackage = appPackage;
  }

  public Bitmap getIcon() {
    return icon;
  }

  public void setIcon(Bitmap icon) {
    this.icon = icon;
  }
}
