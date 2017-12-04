package mvphelper.qiang.com.mvphelper.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import mvphelper.qiang.com.mvphelper.R;
import mvphelper.qiang.com.mvphelper.base.BaseActivity;
import mvphelper.qiang.com.mvphelper.base.Constant;
import mvphelper.qiang.com.mvphelper.databinding.ActivityPicTestBinding;
import mvphelper.qiang.com.mvphelper.domin.ErrorBean;
import mvphelper.qiang.com.mvphelper.domin.ModifyHeadBean;
import mvphelper.qiang.com.mvphelper.mvp.contract.NetContract;
import mvphelper.qiang.com.mvphelper.mvp.presenter.PicTestPresenter;
import mvphelper.qiang.com.mvphelper.utils.ImageUtil;
import mvphelper.qiang.com.mvphelper.utils.LogUtil;
import mvphelper.qiang.com.mvphelper.utils.PhotoUtil;
import mvphelper.qiang.com.mvphelper.utils.ToastUtil;

public class PicTestActivity extends BaseActivity<ActivityPicTestBinding, PicTestPresenter> implements NetContract.INetView {

    private static final int SELECT_PHOTO = 1;
    private static final int TAKE_PHOTO = 2;
    private Uri imageUri;
    private static final int UPLOADPIC_TAG = 100;

    @Override
    protected PicTestPresenter creatPresenter() {
        return new PicTestPresenter(this);
    }

    @Override
    protected void initEvent() {
        mBinding.buttonChoosepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RxPermissions(provideActivity()).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean)
                                showPhotoSelect();
                            else
                                ToastUtil.showToast("open storage permission");
                        });

            }
        });
    }

    private void showPhotoSelect() {
        CharSequence[] items = {"相册", "相机"};
        new AlertDialog.Builder(this)
                .setTitle("选择图片来源")
                .setItems(items, (dialog, which) -> {
                    if (which == 0) {
//                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//                        intent.setType("image/*");
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, SELECT_PHOTO);
                    } else {
                        new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(aBoolean -> {
                            if (aBoolean) {
                                //创建File对象，用于存储拍照后的图片
                                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                                try {
                                    if (outputImage.exists()) {
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (Build.VERSION.SDK_INT >= 24) {
                                    imageUri = FileProvider.getUriForFile(this, Constant.AUTHORITY, outputImage);
                                } else {
                                    imageUri = Uri.fromFile(outputImage);
                                }
                                //启动相机程序
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(intent, TAKE_PHOTO);
                            } else {
                                ToastUtil.showToast("您拒绝了使用相机的权限,请开启后再使用此功能");
                            }
                        });
                    }
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ClipActivity.CUT_REQUEST:
                if (resultCode == RESULT_OK) {
                    String clipPath = data.getStringExtra(ClipActivity.CLIP);
                    File newFile = new File(getExternalCacheDir(), "report.jpg");
                    ImageUtil.compressBmpToFile(this, clipPath, newFile);
                    mPresenter.uploadPic(newFile, UPLOADPIC_TAG);
                    displayImage(clipPath);
                }
                break;
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    String path = PhotoUtil.getPath(provideActivity(), data.getData());
                    Intent intent = new Intent(provideActivity(), ClipActivity.class);
                    intent.putExtra(ClipActivity.PATH, path);
                    startActivityForResult(intent, ClipActivity.CUT_REQUEST);

                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mBinding.ivPic.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {

                    }

                }
                break;
        }
    }


    /**
     * 根据图片路径显示图片的方法
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            mBinding.ivPic.setImageBitmap(bitmap);
        } else {
            ToastUtil.showToast("failed to get image");
        }
    }

    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_pic_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    public void showLoading() {
        LogUtil.i("picccccshowloading");
    }

    @Override
    public void hideLoading() {
        LogUtil.i("piccccchideloading");
    }

    @Override
    public void showError(ErrorBean errorBean, int tag) {
        LogUtil.i("error" + errorBean.desc);
    }

    @Override
    public void setData(ErrorBean errorBean, int tag) {
        if (errorBean instanceof ModifyHeadBean) {
            ModifyHeadBean bean = (ModifyHeadBean) errorBean;
            LogUtil.i(bean.getHead());
        }
    }

    @Override
    public void progress(int precent, int tag) {
        LogUtil.i("piccccccprogress", precent + "____" + tag);
    }
}
