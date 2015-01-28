package com.bitmapfun.util;

import android.content.Context;
import android.graphics.Bitmap;
import com.xhr.GoodGallery.BuildConfig;

/**
 * Created by xhrong on 2015/1/21.
 */
public class LocalImageFetcher extends ImageResizer {

    private static final String TAG = "LocalImageFetcher";
    private static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
    public static final String HTTP_CACHE_DIR = "http";

    /**
     * Initialize providing a target image width and height for the processing images.
     *
     * @param context
     * @param imageWidth
     * @param imageHeight
     */
    public LocalImageFetcher(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
    }

    /**
     * Initialize providing a single target image size (used for both width and height);
     *
     * @param context
     * @param imageSize
     */
    public LocalImageFetcher(Context context, int imageSize) {
        super(context, imageSize);
    }


    /**
     * The main process method, which will be called by the ImageWorker in the AsyncTask background
     * thread.
     *
     * @param data The data to load the bitmap, in this case, a regular http URL
     * @return The downloaded and resized bitmap
     */
    private Bitmap processBitmap(String data) {
        if (BuildConfig.DEBUG) {
//            Log.d(TAG, "processBitmap - " + data);
        }
        // Return a sampled down version
        return decodeSampledBitmapFromFile(data, mImageWidth, mImageHeight);
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap(String.valueOf(data));
    }
}
