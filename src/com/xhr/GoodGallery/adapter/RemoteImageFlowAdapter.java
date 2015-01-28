package com.xhr.GoodGallery.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.bitmapfun.util.ImageFetcher;
import com.huewu.pla.lib.ScaleImageView;
import com.xhr.GoodGallery.R;
import com.xhr.GoodGallery.RemoteGalleryActivity;
import com.xhr.GoodGallery.model.RemotePicInfo;
import com.xhr.GoodGallery.utils.Pair;

import java.util.List;

/**
 * Created by xhrong on 2015/1/16.
 */
public class RemoteImageFlowAdapter extends BaseAdapter {
    private static final String TAG = "LocalImageFlowAdapter";

    private List<RemotePicInfo> mImageList;
    private LayoutInflater mLayoutInflater;
    int imageWidth;
    ImageFetcher imageFetcher;
    Context mContext;

    public RemoteImageFlowAdapter(Context context, List<RemotePicInfo> list) {
        mImageList = list;
        mLayoutInflater = LayoutInflater.from(context);

        mContext = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Configuration mConfiguration = context.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {//横屏
            imageWidth = wm.getDefaultDisplay().getWidth() / context.getResources().getInteger(R.integer.multicolumnpulltorefresh_plaLandscapecolumnnumber);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            imageWidth = wm.getDefaultDisplay().getHeight() / context.getResources().getInteger(R.integer.multicolumnpulltorefresh_placolumnnumber);
        }

        imageFetcher = new ImageFetcher(context, imageWidth);
        imageFetcher.setLoadingImage(R.drawable.empty_photo);
        imageFetcher.setNoImage(R.drawable.no_image);
    }

    public int getCount() {
        return mImageList.size();
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.infos_list, null);
            holder = new ViewHolder();
            holder.imageView = (ScaleImageView) view.findViewById(R.id.news_pic);
            holder.textView = (TextView) view.findViewById(R.id.news_title);
            holder.dateTextView = (TextView) view.findViewById(R.id.news_time);
            view.setTag(holder);
        }
        holder = (ViewHolder) view.getTag();
        RemotePicInfo picInfo=mImageList.get(i);
        int imageHeight = (int) ((picInfo.picHeight + 0f) / picInfo.picWidth * imageWidth);
        imageFetcher.setImageSize(imageWidth, imageHeight);
        holder.imageView.setImageWidth(imageWidth);
        holder.imageView.setImageHeight(imageHeight);

        imageFetcher.loadImage(picInfo.picRawUrl, holder.imageView);
        holder.textView.setText(picInfo.picDescription);
      //  holder.dateTextView.setText(mImageList.get(i).picDate);
        Pair<Integer, RemotePicInfo> curImage = new Pair<Integer, RemotePicInfo>();
        curImage.first = i;
        curImage.second = mImageList.get(i);
        holder.imageView.setTag(curImage);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<Integer, RemotePicInfo> pair = (Pair<Integer, RemotePicInfo>) view.getTag();
                if (pair != null) {
                    Intent intent = new Intent(mContext, RemoteGalleryActivity.class);
                    intent.putExtra("imgurl", pair.second.picRawUrl);
                    intent.putExtra("index", pair.first);
                    mContext.startActivity(intent);
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        ScaleImageView imageView;
        TextView textView;
        TextView dateTextView;
    }
}
