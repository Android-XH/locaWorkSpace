package com.example.worktools.util;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BitmapUtil {
    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri)  {
        try {
            InputStream input = ac.getContentResolver().openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;
            //图片分辨率以480x800为标准
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            //比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = be;//设置缩放比例
            bitmapOptions.inDither = true;//optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            input = ac.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return compressImage(bitmap);//再进行质量压缩
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       return null;
    }
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    public static String saveBitmap(String path, Bitmap bitmap) {
        try {
            if (path != null && bitmap != null) {
                File _file = new File(path);
                // 如果文件夹不存在则创建一个新的文件
                if (!_file.exists()) {
                    _file.getParentFile().mkdirs();
                    _file.createNewFile();
                }
                // 创建输出流
                OutputStream write = new FileOutputStream(_file);
                // 获取文件名
                String fileName = _file.getName();
                // 取出文件的格式名
                String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if ("png".equalsIgnoreCase(endName)) {
                    // bitmap的压缩格式
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, write);
                    write.close();
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, write);
                    write.close();
                }
//                try {
//                    MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                            _file .getAbsolutePath(), fileName, null);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                // 最后通知图库更新
//                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri uri = Uri.fromFile(_file);
//                intent.setData(uri);
//                context.sendBroadcast(intent);
                return _file.getPath();
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
    /**
     * 获取屏幕bitmap对象
     *
     * @return
     */
    public static void takeScreenShot(View view, ResultCallback<Bitmap> callback) {
        // 创建对应大小的bitmap
        AsyncTask.execute(() -> {
            int height = 0;
            if (view.getTag() != null) {
                height = (int) view.getTag();
            }
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            if (bitmap == null) {
                callback.onFail("保存图片失败");
            } else {
                callback.onSuccess(bitmap);
            }
        });
    }
}
