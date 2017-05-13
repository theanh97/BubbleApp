package com.example.dell.bubbleapp.Utils;

import android.graphics.drawable.shapes.Shape;

import static android.icu.lang.UCharacter.DecompositionType.CIRCLE;

/**
 * Created by DELL on 07/05/2017.
 */

public class Constant {
  // SEEK BAR SIZE
  public static final int SEEKBAR_MAX_ICON_SIZE = 150;
  public static final int SEEKBAR_MIN_ICON_SIZE = 70;
  public static final int SEEKBAR_MIDDLE_SIZE = (SEEKBAR_MAX_ICON_SIZE - SEEKBAR_MIN_ICON_SIZE) /
      2 + SEEKBAR_MIN_ICON_SIZE;

  // DIRECTORY CODE
  public static final int DIRECTORY_REQUEST_CODE = 1;
  public static final int DIRECTORY_RESULT_CODE = 2;

  // TAG
  public static final String TAG = "tag123";

  // SHAPE ENUM
  public enum Shape {
    None,
    Circle,
    Square,
    Round,
    Star,
    Sketch;
  }

  // APP INFO
  public static final String APP_INFO =
      "\t- Ứng dụng : Bubble App" +
          "\n\n\t- Phiên bản : 1.0" +
          "\n\n\t- Tính năng : " +
          "\n\n\t\t\t+ Tạo ra thể hiện của các ứng dụng trên màn hình " +
          "\n\n\t\t\t+ Có thể cấu hình lại các hình ảnh theo các dạng khối ( tròn , vuông , ... )" +
          "\n\n\t\t\t+ Có thể sử dụng ảnh động làm icon" +
          "\n\n\t- Tác giả : Phạm Thế Anh";
}
