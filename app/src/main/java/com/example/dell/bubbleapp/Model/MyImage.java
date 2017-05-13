package com.example.dell.bubbleapp.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

import static android.R.attr.x;

/**
 * Created by DELL on 09/05/2017.
 */

public class MyImage implements Serializable {
  private String location;
  private int width;
  private int height;

  public static boolean isGifImage(MyImage image) {
    if (image.location.isEmpty() || !image.location.toLowerCase().contains(".gif"))
      return false;
    return true;
  }

  public MyImage(String location) {
    this.setLocation(location);
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
