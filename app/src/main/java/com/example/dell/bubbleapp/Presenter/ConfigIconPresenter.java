package com.example.dell.bubbleapp.Presenter;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.dell.bubbleapp.Model.AddingModel;
import com.example.dell.bubbleapp.Model.MyImage;
import com.example.dell.bubbleapp.Presenter.Interface.ConfigFragmentInterface;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.Utils.Constant;
import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.Utils.LoadImageTransformation;
import com.example.dell.bubbleapp.View.ConfigIconFragment;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.EventBus;

import static android.R.attr.gravity;
import static android.R.attr.max;
import static android.R.attr.width;

/**
 * Created by DELL on 05/05/2017.
 */

public class ConfigIconPresenter implements DiscreteSeekBar.OnProgressChangeListener, ConfigFragmentInterface {
  ConfigIconFragment mView;
  App mApp;
  int tempIndicator = Constant.SEEKBAR_MIDDLE_SIZE;

  public ConfigIconPresenter(ConfigIconFragment view, App app) {
    this.mView = view;
    this.mApp = app;
    app.setMyImage(new MyImage(""));
    prepareUI();
  }

  @Override
  public void resizeIcon(int maxSize, int w, int h) {
    float rate = (float) w / (float) h;
    if (rate < 1) {
      h = maxSize;
      w = (int) (h * rate);
    } else {
      w = maxSize;
      h = (int) (w / rate);
    }

    mApp.getMyImage().setWidth(w);
    mApp.getMyImage().setHeight(h);

    Log.d(Constant.TAG, "Rate : " + rate);
    Log.d(Constant.TAG, "Width : " + w);
    Log.d(Constant.TAG, "Height : " + h);
  }

  public void prepareUI() {
    // Seek bar
    mView.sbSize.setMax(Constant.SEEKBAR_MAX_ICON_SIZE);
    mView.sbSize.setMin(Constant.SEEKBAR_MIN_ICON_SIZE);
    mView.sbSize.setProgress(Constant.SEEKBAR_MIDDLE_SIZE);
    mView.sbSize.setOnProgressChangeListener(this);

    // image
    resizeIcon(Constant.SEEKBAR_MIDDLE_SIZE, mApp.getIcon().getWidth(), mApp.getIcon().getHeight());
    formatViewParams(mView.ivIcon, mApp.getMyImage().getWidth(),
        mApp.getMyImage().getHeight(), Gravity.CENTER_HORIZONTAL);
    mView.ivIcon.setImageBitmap(mApp.getIcon());
  }

  @Override
  public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int i, boolean b) {
    int indicator = discreteSeekBar.getProgress();
    resizeIcon(indicator, mApp.getIcon().getWidth(), mApp.getIcon().getHeight());
    formatViewParams(mView.ivIcon, mApp.getMyImage().getWidth(), mApp.getMyImage().getHeight(), Gravity.CENTER_HORIZONTAL);
    tempIndicator = indicator;
  }

  @Override
  public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {

  }

  @Override
  public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {

  }

  @Override
  public void updateAppIcon(Bitmap icon, String iconPath) {
    mView.edtDirectory.setText(iconPath);
    mApp.setIcon(icon);
    mApp.getMyImage().setLocation(iconPath);
    Log.d(Constant.TAG, "(" + mApp.getIcon().getWidth() + " , " + mApp.getIcon().getHeight() + " ) " +
        "");

    resizeIcon(Constant.SEEKBAR_MIDDLE_SIZE, mApp.getIcon().getWidth(), mApp.getIcon().getHeight());
    formatViewParams(mView.ivIcon, mApp.getMyImage().getWidth(), mApp.getMyImage().getHeight(),
        Gravity.CENTER_HORIZONTAL);

    Log.d(Constant.TAG, "(" + mApp.getIcon().getWidth() + " , " + mApp.getIcon().getHeight() + " ) " +
        "");
    // set icon
    if (MyImage.isGifImage(mApp.getMyImage())) {
      final ProgressDialog progressDialog = ProgressDialog.show(
          mView.getContext(),
          mView.getContext().getString(R.string.update_gif_image),
          null,
          true,
          false);


      Glide.with(mView)
          .load(mApp.getMyImage().getLocation())
          .asGif()
          .placeholder(R.drawable.loading)
          .listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
              return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
              progressDialog.cancel();
              return false;
            }
          })
          .into(mView.ivIcon);
    } else {
      mView.ivIcon.setImageBitmap(mApp.getIcon());
    }
  }

  @Override
  public void formatViewParams(ImageView view, int width, int height, int
      gravity) {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
    layoutParams.gravity = gravity;
    view.setLayoutParams(layoutParams);

  }

  @Override
  public void updateTransformationAppIcon(Transformation[] transformations) {
    resizeIcon(Constant.SEEKBAR_MIDDLE_SIZE, mView.mApp.getIcon().getWidth(), mView.mApp.getIcon()
        .getHeight());
    formatViewParams(mView.ivIcon, mApp.getMyImage().getWidth(), mApp.getMyImage().getHeight(), Gravity.CENTER_HORIZONTAL);
    new LoadImageTransformation(mView.ivIcon, transformations, mApp).execute(mView.mApp.getIcon());
    mView.sbSize.setProgress(Constant.SEEKBAR_MIDDLE_SIZE);
  }

  @Override
  public void uploadNewAppBubble() {
    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mApp.getMyImage().getWidth(), mApp.getMyImage().getHeight());
    EventBus.getDefault().post(new App(
        mApp.getAppName(),
        mApp.getAppPackage(),
        mApp.getIcon(),
        mApp.getMyImage(),
        params
    ));
    mView.getDialog().cancel();
  }

  @Override
  public void updateMyImage(String newImageLocation) {
    mApp.getMyImage().setLocation(newImageLocation);
    if (MyImage.isGifImage(mApp.getMyImage())) {
      mView.spnShape.setClickable(false);
      mView.spnShape.setBackgroundResource(R.color.spinner_disable);
    } else {
      mView.spnShape.setClickable(true);
      mView.spnShape.setBackgroundResource(R.color.spinner_normal);
    }
  }
}
