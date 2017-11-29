package mvphelper.qiang.com.mvphelper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mvphelper.qiang.com.mvphelper.base.App;

import static mvphelper.qiang.com.mvphelper.base.Constant.STORAGE_PATH;

/**
 * Created by sh on 2016/4/15 16:35.
 */
public class ImageUtil {
    public static void compressBmpToFile(Context context, String path, File newFile) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        int width = options.outWidth;
        int height = options.outHeight;
        int biliW = width / widthPixels;
        int biliH = height / heightPixels;
        options.inJustDecodeBounds = false;
        options.inSampleSize = Math.max(biliW, biliH);
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int num = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, num, baos);
        while (baos.toByteArray().length / 1024 > 200) {
            if (num <= 10) {
                break;
            }
            baos.reset();
            num -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, num, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void compressBmpToFile(Context context, String path, File newFile, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        int width = options.outWidth;
        int height = options.outHeight;
        int biliW = width / widthPixels;
        int biliH = height / heightPixels;
        options.inJustDecodeBounds = false;
        options.inSampleSize = Math.max(biliW, biliH);
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        if (bitmap == null)
            return;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int num = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, num, baos);
        while (baos.toByteArray().length / 1024 > 512) {
            if (num <= 10) {
                break;
            }
            baos.reset();
            num -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, num, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File bmpCreatAndCompressToFile(Context context, String filename, Bitmap bitmap) {
        File file = new File(STORAGE_PATH + File.separator + "copy" + filename);//将要保存图片的路径
        Bitmap robitmap = convertBmp(bitmap);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            robitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            robitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File newFile = new File(STORAGE_PATH + File.separator + filename);
        compressBmpToFile(context, file.getPath(), newFile);
        return newFile;
    }

    public static Bitmap convertBmp(Bitmap bmp) {
        int w = bmp.getWidth();
        int h = bmp.getHeight();

        Bitmap convertBmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(convertBmp);
        Matrix matrix = new Matrix();
        matrix.postScale(1, -1); //镜像垂直翻转
//  matrix.postScale(-1, 1); //镜像水平翻转
//  matrix.postRotate(-90); //旋转-90度

        Bitmap newBmp = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
        cv.drawBitmap(newBmp, new Rect(0, 0, newBmp.getWidth(), newBmp.getHeight()), new Rect(0, 0, w, h), null);
        return convertBmp;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, ArmsUtils.dip2px(App.getInstance(), 104), ArmsUtils.dip2px(App.getInstance(), 78));

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static File bmpCreatAndCompressShare(Context context, String filename, Bitmap bitmap) {
        File file = new File(STORAGE_PATH + File.separator + "copy" + filename);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File newFile = new File(STORAGE_PATH + File.separator + filename);
        compressBmpToFile(context, file.getPath(), newFile);
        return newFile;
    }
}