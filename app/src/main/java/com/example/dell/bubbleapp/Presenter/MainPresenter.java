package com.example.dell.bubbleapp.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.example.dell.bubbleapp.Adapter.AppAdapter;
import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.Model.AppModule;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.Service.AppService;
import com.example.dell.bubbleapp.Utils.Constant;
import com.example.dell.bubbleapp.View.AppInfoFragment;
import com.example.dell.bubbleapp.View.ConfigIconFragment;
import com.example.dell.bubbleapp.View.MainActivity;
import com.example.dell.bubbleapp.Presenter.Interface.MainActivityInterface;

import java.util.ArrayList;

/**
 * Created by DELL on 05/05/2017.
 */

public class MainPresenter implements MainActivityInterface {

  private MainActivity mView;
  private ArrayList<App> appModule;
  private ArrayList<App> appSearch;
  private AppAdapter mAppAdapter;
  private RecyclerView mRecyclerView;

  public MainPresenter(MainActivity activity) {
    this.mView = activity;
    initAppModule();
    setRecyclerView();
    startService();
  }


  @Override
  public void showInfoDialog() {
    AppInfoFragment alertdFragment = new AppInfoFragment();
    alertdFragment.show(mView.getSupportFragmentManager(), "AppInfo");
  }

  @Override
  public void findApp(String inputApp) {
    ArrayList<App> arrayList = new ArrayList<App>();
    for (App item : appSearch) {
      String input = inputApp.toLowerCase();
      String appName = item.getAppName().toLowerCase();
      if (appName.contains(input)) {
        arrayList.add(item);
      }
    }
    appModule.clear();
    appModule.addAll(arrayList);
    mAppAdapter.notifyDataSetChanged();
  }

  @Override
  public void initAppModule() {
    appSearch = new ArrayList<App>();
    appSearch.addAll(AppModule.getAppModule(mView.getApplicationContext()));
    appModule = AppModule.getAppModule(mView.getApplicationContext());
  }

  @Override
  public void setRecyclerView() {
    mAppAdapter = new AppAdapter(mView, appModule, mView.getSupportFragmentManager());
    mView.mRecyclerView.setAdapter(mAppAdapter);
  }

  @Override
  public void startService() {
    Intent intent = new Intent(mView, AppService.class);
    mView.startService(intent);
  }
}
