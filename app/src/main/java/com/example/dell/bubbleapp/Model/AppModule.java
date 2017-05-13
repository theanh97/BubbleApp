package com.example.dell.bubbleapp.Model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.dell.bubbleapp.Utils.Constant;
import com.example.dell.bubbleapp.Utils.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 11/10/2016.
 */

public class AppModule {
  public static ArrayList<App> appModule;

  public static ArrayList<App> getAppModule(Context context) {

    if (appModule == null) {
      appModule = new ArrayList<App>();
      final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
      mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
      final List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(
          mainIntent, 0);
      for (int i = 0; i < pkgAppsList.size(); i++) {
        ResolveInfo appInfo = pkgAppsList.get(i);
        String appName = (String) appInfo.activityInfo.loadLabel(context.getPackageManager());
        appName = appName.toLowerCase();

        Drawable drawable = appInfo.activityInfo.loadIcon(context.getPackageManager());
        Bitmap icon = ImageUtils.drawableToBitmap(drawable);

        String appPackageName = appInfo.activityInfo.packageName;
        App app = new App(appName, appPackageName, icon);
        appModule.add(app);
      }
    }
    return appModule;
  }
}
