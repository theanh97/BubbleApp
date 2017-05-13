package com.example.dell.bubbleapp.View;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dell.bubbleapp.Presenter.MainPresenter;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.Utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

  private SearchView searchView = null;
  private SearchView.OnQueryTextListener queryTextListener;

  private GridLayoutManager lLayout;
  private MainPresenter mMainPresenter;

  @BindView(R.id.my_toolbar)
  Toolbar mToolbar;

  @BindView(R.id.recycler_view)
  public RecyclerView mRecyclerView;

//  @BindView(R.id.edit_text_search)
//  public EditText mEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    prepareUI();
    mMainPresenter = new MainPresenter(this);
  }

  public void prepareUI() {
    // Action bar
//    getSupportFragmentManager();
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(false);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    View actionbarView = getLayoutInflater().inflate(R.layout.cus_actionbar, null, false);
    getSupportActionBar().setCustomView(actionbarView);
    getSupportActionBar().setDisplayShowCustomEnabled(true);

    // RecyclerView
    lLayout = new GridLayoutManager(MainActivity.this, 4);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setLayoutManager(lLayout);

    // SearchView
//    mEditText.addTextChangedListener(this);

  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
    mMainPresenter.findApp(s.toString());
  }

  @Override
  public void afterTextChanged(Editable s) {

  }

  @Override
  public void onClick(View v) {
    mMainPresenter.showInfoDialog();
  }
}
