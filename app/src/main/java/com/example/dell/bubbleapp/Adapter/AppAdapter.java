package com.example.dell.bubbleapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.View.ConfigIconFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by DELL on 05/05/2017.
 */

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.RecyclerViewHolders>{
  private List<App> mListApp;
  private Context mContext;
  private FragmentManager mFragmentManager;
  public AppAdapter(Context context, List<App> itemList , FragmentManager fragmentManager) {
    this.mListApp = itemList;
    this.mContext = context;
    this.mFragmentManager = fragmentManager;
  }

  @Override
  public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

    View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_lv_row, null);
    RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
    return rcv;
  }

  @Override
  public void onBindViewHolder(RecyclerViewHolders holder, int position) {
    holder.appName.setText(mListApp.get(position).getAppName());
    holder.appIcon.setImageBitmap(mListApp.get(position).getIcon());
    holder.app = mListApp.get(position);
  }

  @Override
  public int getItemCount() {
    return this.mListApp.size();
  }

  public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView appName;
    public ImageView appIcon;
    public App app;

    public RecyclerViewHolders(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      appName = (TextView) itemView.findViewById(R.id.image_view_app_name);
      appIcon = (ImageView) itemView.findViewById(R.id.image_view_app_icon);
    }

    @Override
    public void onClick(View view) {
      ConfigIconFragment alertdFragment = new ConfigIconFragment();
      Bundle bundle = new Bundle();
      bundle.putSerializable("app",new App(app.getAppName(),app.getAppPackage(),app.getIcon()));
      alertdFragment.setArguments(bundle);
      alertdFragment.show(mFragmentManager, "ConfigIcon");
    }
  }

}
