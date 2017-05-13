package com.example.dell.bubbleapp.Presenter.Interface;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.example.dell.bubbleapp.Model.App;

import static android.R.attr.width;

/**
 * Created by DELL on 10/05/2017.
 */

public interface ConfigFragmentInterface {
  public void updateAppIcon(Bitmap icon, String iconPath);

  public void formatViewParams(ImageView view, int width, int height, int
      gravity);

  public void updateTransformationAppIcon(Transformation[] transformations);

  public void uploadNewAppBubble();

  public void updateMyImage(String newImageLocation);

  public void resizeIcon(int maxSize , int width , int height);
}
