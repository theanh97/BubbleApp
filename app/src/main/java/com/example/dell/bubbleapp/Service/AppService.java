package com.example.dell.bubbleapp.Service;

import android.app.ProgressDialog;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.dell.bubbleapp.Model.AddingModel;
import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.Model.AppModule;
import com.example.dell.bubbleapp.Model.MyImage;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.Utils.Constant;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * Created by DELL on 11/10/2016.
 */

public class AppService extends Service {
  public static BubblesManager bubblesManager;
  public static int flag = 0;
  private ArrayList<BubbleLayout> array = null;
  PackageManager pm = null;

  @Override
  public void onCreate() {
    initializeBubblesManager();
    super.onCreate();
    array = new ArrayList<BubbleLayout>();
  }

  private void initializeBubblesManager() {
    bubblesManager = new BubblesManager.Builder(this)
        .setTrashLayout(R.layout.bubble_trash_layout)
        .build();
    bubblesManager.initialize();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (!EventBus.getDefault().isRegistered(this))
      EventBus.getDefault().register(this);
    pm = getApplicationContext().getPackageManager();
    return Service.START_STICKY_COMPATIBILITY;
  }


  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(Constant.TAG, "STOP SERVICE");
  }

  private void addNewBubble(String appName, final String appPackage, Bitmap icon, FrameLayout
      .LayoutParams params, MyImage myImage) {

    final BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout
        .bubble_layout, null);
    bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
      @Override
      public void onBubbleRemoved(BubbleLayout bubble) {
      }
    });
    flag++;
    ImageView iv = (ImageView) bubbleView.findViewById(R.id.avatar);

    // .JPG , .PNG
    if (!MyImage.isGifImage(myImage)) {
      iv.setImageBitmap(icon);
      EventBus.getDefault().post(new AddingModel(true));
    }
    // .GIF
    else {
      Glide.with(this)
          .load(myImage.getLocation())
          .asGif()
          .placeholder(R.drawable.loading)
          .listener(new RequestListener<String, GifDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GifDrawable> target, boolean isFirstResource) {
              return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, String model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
              EventBus.getDefault().post(new AddingModel(true));
              return false;
            }
          })
          .into(iv);
    }
    iv.setLayoutParams(params);


    bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

      @Override
      public void onBubbleClick(BubbleLayout bubble) {
        Intent intent = pm.getLaunchIntentForPackage(appPackage);
        startActivity(intent);
      }
    });
    bubbleView.setShouldStickToWall(true);
    bubbleView.setTag(appName);
    array.add(bubbleView);
    switch (flag % 6) {
      case 1:
        bubblesManager.addBubble(bubbleView, 20, 200);
        break;
      case 2:
        bubblesManager.addBubble(bubbleView, 5, 400);
        break;
      case 3:
        bubblesManager.addBubble(bubbleView, 5, 600);
        break;
      case 4:
        bubblesManager.addBubble(bubbleView, 5, 800);
        break;
      case 5:
        bubblesManager.addBubble(bubbleView, 5, 1000);
        break;
      case 0:
        bubblesManager.addBubble(bubbleView, 5, 1200);
        break;
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void addBubble1(App app) {
    addNewBubble(app.getAppName(), app.getAppPackage(), app.getIcon(), app.getLayoutParams(), app
        .getMyImage());
  }

}
