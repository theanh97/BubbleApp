package com.example.dell.bubbleapp.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.example.dell.bubbleapp.R;

import java.io.ByteArrayOutputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DELL on 07/05/2017.
 */

public class ImageUtils {
  public static Bitmap drawableToBitmap(Drawable drawable) {
    Bitmap bitmap = null;

    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if (bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }

    if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
      bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
    } else {
      bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

  @SuppressLint("NewApi")
  public static String getRealPathFromURI_API19(Context context, Uri uri) {
    String filePath = "";
    String wholeID = DocumentsContract.getDocumentId(uri);

    // Split at colon, use second item in the array
    String id = wholeID.split(":")[1];

    String[] column = {MediaStore.Images.Media.DATA};

    // where id is equal to
    String sel = MediaStore.Images.Media._ID + "=?";

    Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        column, sel, new String[]{id}, null);

    int columnIndex = cursor.getColumnIndex(column[0]);

    if (cursor.moveToFirst()) {
      filePath = cursor.getString(columnIndex);
    }
    cursor.close();
    return filePath;
  }

  @SuppressLint("NewApi")
  public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
    String[] proj = {MediaStore.Images.Media.DATA};
    String result = null;

    CursorLoader cursorLoader = new CursorLoader(
        context,
        contentUri, proj, null, null, null);
    Cursor cursor = cursorLoader.loadInBackground();

    if (cursor != null) {
      int column_index =
          cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
      cursor.moveToFirst();
      result = cursor.getString(column_index);
    }
    return result;
  }

  public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) {
    String[] proj = {MediaStore.Images.Media.DATA};
    Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
    int column_index
        = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    return cursor.getString(column_index);
  }

}
