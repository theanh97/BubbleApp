package com.example.dell.bubbleapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.dell.bubbleapp.R;

/**
 * Created by DELL on 12/05/2017.
 */

public class AppInfoFragment extends DialogFragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_app_info, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
  }
}
