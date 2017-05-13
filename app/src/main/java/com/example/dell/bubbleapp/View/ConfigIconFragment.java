package com.example.dell.bubbleapp.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.dell.bubbleapp.Model.AddingModel;
import com.example.dell.bubbleapp.Utils.Constant;
import com.example.dell.bubbleapp.Model.App;
import com.example.dell.bubbleapp.Presenter.ConfigIconPresenter;
import com.example.dell.bubbleapp.R;
import com.example.dell.bubbleapp.Utils.ImageUtils;
import com.example.dell.bubbleapp.Utils.LoadImageTransformation;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;

import static android.R.attr.data;
import static android.R.string.cancel;
import static android.app.Activity.RESULT_OK;
import static android.icu.lang.UCharacter.DecompositionType.CIRCLE;
import static com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE;
import static com.example.dell.bubbleapp.Utils.Constant.Shape.None;
import static com.example.dell.bubbleapp.Utils.Constant.Shape.Square;
import static com.example.dell.bubbleapp.Utils.Constant.Shape.Star;
import static com.example.dell.bubbleapp.Utils.ImageUtils.drawableToBitmap;

/**
 * Created by DELL on 05/05/2017.
 */

public class ConfigIconFragment extends DialogFragment {
  public App mApp;
  ConfigIconPresenter mPresenter;
  public ImageView ivIcon;
  public DiscreteSeekBar sbSize;
  public Button btnDirectory;
  public EditText edtDirectory;
  public MaterialSpinner spnShape;
  public Button btnOK;
  public Button btnCancel;
  private ProgressDialog mAddingGifImageDialog;

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    EventBus.getDefault().register(this);
    findViewByIds(view);
    setViewEvents();
    mApp = (App) getArguments().getSerializable("app");
    mPresenter = new ConfigIconPresenter(this, new App(mApp.getAppName(), mApp.getAppPackage(),
        mApp.getIcon()));
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//    getDialog().setTitle("App Icon");
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View mView = inflater.inflate(R.layout.fragment_config_icon, container, false);
    return mView;
  }

  private void findViewByIds(View v) {
    ivIcon = (ImageView) v.findViewById(R.id.image_view_app_icon);
    sbSize = (DiscreteSeekBar) v.findViewById(R.id.seek_bar_size);
    btnDirectory = (Button) v.findViewById(R.id.button_image);
    edtDirectory = (EditText) v.findViewById(R.id.edit_text_image);
    spnShape = (MaterialSpinner) v.findViewById(R.id.spinner_shape);
    btnOK = (Button) v.findViewById(R.id.button_ok);
    btnCancel = (Button) v.findViewById(R.id.button_cancel);
  }

  private void setViewEvents() {
    // Directory
    btnDirectory.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose App Icon"), Constant.DIRECTORY_REQUEST_CODE);
      }
    });

    edtDirectory.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        mPresenter.updateMyImage(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    // Shape
    spnShape.setItems(Constant.Shape.values());
    spnShape.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
      @Override
      public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        Constant.Shape a = (Constant.Shape) view.getItems().get(position);
        Context context = view.getContext();
        switch (a) {
          case None:
            mPresenter.updateTransformationAppIcon(null);
            break;
          case Circle:
            mPresenter.updateTransformationAppIcon(new Transformation[]{new CropCircleTransformation(context)});
            break;
          case Square:
            mPresenter.updateTransformationAppIcon(new Transformation[]{new
                CropSquareTransformation(context)});
            break;
          case Round:
            mPresenter.updateTransformationAppIcon(new Transformation[]{new RoundedCornersTransformation(context,
                30, 0,
                RoundedCornersTransformation.CornerType.ALL)});
            break;
          case Star:
            mPresenter.updateTransformationAppIcon(new Transformation[]{new CenterCrop(context),
                new MaskTransformation(context, R.drawable.mask_starfish)});
            break;
          case Sketch:
            mPresenter.updateTransformationAppIcon(new Transformation[]{new
                SketchFilterTransformation(context)});
            break;
          default:
            break;
        }
      }
    });


    // OK  && CANCEL
    btnOK.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAddingGifImageDialog = ProgressDialog.show(getContext(), "",
            getString(R.string.adding_gif_icon), true, false);
        mPresenter.uploadNewAppBubble();
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getDialog().cancel();
      }
    });
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == Constant.DIRECTORY_REQUEST_CODE) {
      if (resultCode == RESULT_OK) {
        Uri selectedimg = data.getData();
        String filePath;
        // SDK < API11
        if (Build.VERSION.SDK_INT < 11)
          filePath = ImageUtils.getRealPathFromURI_BelowAPI11(this.getContext(), data.getData
              ());

          // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
          filePath = ImageUtils.getRealPathFromURI_API11to18(this.getContext(), data.getData
              ());

          // SDK > 19 (Android 4.4)
        else
          filePath = ImageUtils.getRealPathFromURI_API19(this.getContext(), data.getData());

        try {
          Bitmap icon = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(),
              selectedimg);
          mApp.setIcon(icon);
          mPresenter.updateAppIcon(icon, filePath);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        Toast.makeText(this.getContext(), "Load Image Failed", Toast.LENGTH_SHORT).show();
      }
      spnShape.setSelectedIndex(0);
      sbSize.setProgress(Constant.SEEKBAR_MIDDLE_SIZE);
    }
  }

  @Subscribe(threadMode = ThreadMode.BACKGROUND)
  public void cancelAddingIconDialog3(AddingModel addSuccessfull) {
    if (addSuccessfull.isAddingSuccessful)
      mAddingGifImageDialog.cancel();
  }
}
