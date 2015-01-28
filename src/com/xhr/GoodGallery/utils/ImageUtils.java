package com.xhr.GoodGallery.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by xhrong on 2015/1/16.
 */
public class ImageUtils {

    public static Bitmap getThumbnail(String filePath, int targetWidth) {
        Bitmap thumbBitmap = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, opts);
            int imageWidth = opts.outWidth;
            int imageHeight = opts.outHeight;
            int scaleX = imageWidth / targetWidth;
            int scale = scaleX > 1 ? scaleX : 1;
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, opts);

            long startTime = System.nanoTime();

            int targetHeiht = (int) (imageHeight * ((targetWidth + 0F) / imageWidth));
            thumbBitmap = ThumbnailUtils.extractThumbnail(bitmap, targetWidth, targetHeiht);

            long consumingTime = System.nanoTime() - startTime; //消耗時間
            System.out.println(filePath + consumingTime / 1000 + "微秒");
        } catch (Exception e) {
            Log.e("getThumbnail:", filePath);
            e.printStackTrace();
        }

        return thumbBitmap;
    }

    public static Pair<Integer, Integer> getImageSize(String filePath) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, opts);
        Pair<Integer, Integer> size = new Pair<Integer, Integer>();
        size.first = opts.outWidth;
        size.second = opts.outHeight;
        return size;
    }




}
