package com.example.dell.bubbleapp.Utils;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.R;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DELL on 07/05/2017.
 */

public class LoadImageTransformation extends AsyncTask<Bitmap, Void, ByteArrayOutputStream> {
  private ImageView iv;
  private Transformation[] tf;
  private App mApp;
  private ProgressDialog progress;

  public LoadImageTransformation(ImageView imageView, Transformation[] transformations, App app) {
    this.iv = imageView;
    this.tf = transformations;
    this.mApp = app;
    progress = new ProgressDialog(imageView.getContext());
    progress.setMessage(imageView.getContext().getString(R.string.update_shape_transformation));
    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progress.setIndeterminate(true);
    progress.setProgress(0);
    progress.setCancelable(false);
    progress.show();
  }

  @Override
  protected ByteArrayOutputStream doInBackground(Bitmap... params) {
    Bitmap.CompressFormat[] arr = {Bitmap.CompressFormat.JPEG, Bitmap.CompressFormat.PNG};
    Bitmap photo = params[0];
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    return stream;
  }

  @Override
  protected void onPostExecute(ByteArrayOutputStream aVoid) {
    super.onPostExecute(aVoid);
    if (tf == null) {
      Glide.with(iv.getContext())
          .load(aVoid.toByteArray())
          .asBitmap()
          .error(R.drawable.loading)
          .into(iv);
    } else if (tf.length == 1) {
      Glide.with(iv.getContext())
          .load(aVoid.toByteArray())
          .asBitmap()
          .error(R.drawable.loading)
          .transform(tf[0])
          .into(iv);
    } else {
      Glide.with(iv.getContext())
          .load(aVoid.toByteArray())
          .asBitmap()
          .error(R.drawable.loading)
          .transform(tf[0], tf[1])
          .into(iv);
    }
    Handler a = new Handler();
    a.postDelayed(new Runnable() {
      @Override
      public void run() {
        mApp.setIcon(ImageUtils.drawableToBitmap(iv.getDrawable()));
        progress.cancel();
      }
    }, 1000);
  }
}
