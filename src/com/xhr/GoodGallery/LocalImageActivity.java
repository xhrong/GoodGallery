package com.xhr.GoodGallery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huewu.pla.lib.XListView;
import com.xhr.GoodGallery.adapter.LocalImageFlowAdapter;
import com.xhr.GoodGallery.model.LocalPicInfo;
import com.xhr.GoodGallery.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class LocalImageActivity extends Fragment implements Handler.Callback {

    String TAG = "LocalImageActivity";

    private final static int SCAN_OK = 0x001;

    private XListView mAdapterView = null;
    private LocalImageFlowAdapter mAdapter = null;

    private LinkedList<LocalPicInfo> fileNameList = new LinkedList<LocalPicInfo>();
    public Handler myHandler = new Handler(this);


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.local_image);
//
//        mAdapterView = (XListView) findViewById(R.id.list);
//        mAdapterView.setPullLoadEnable(false);
//        mAdapterView.setPullRefreshEnable(false);
//
//        getSDCardImages(this);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.local_image, container, false);
        mAdapterView = (XListView) view.findViewById(R.id.list);
        mAdapterView.setPullLoadEnable(false);
        mAdapterView.setPullRefreshEnable(false);

        getSDCardImages(getActivity());
        return view;

    }


    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void getSDCardImages(final Context context) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED
                );

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    LocalPicInfo localPicInfo = new LocalPicInfo();
                    localPicInfo.picRawPath = path;
                    localPicInfo.picDescription = FileUtils.getFileNameFromPath(path);
                    localPicInfo.picName = FileUtils.getFileNameFromPath(path);
                    localPicInfo.picDate = new SimpleDateFormat("yyyy年MM月dd日").format(new Date(Long.parseLong((mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)))) * 1000));
                    fileNameList.add(localPicInfo);
                }
                mCursor.close();
                GGApplication.setLocalPicInfoList(fileNameList);
                //通知Handler扫描图片完成
                myHandler.sendEmptyMessage(SCAN_OK);

            }
        }).start();

    }

    private void initAdapter() {
        mAdapter = new LocalImageFlowAdapter(getActivity(), fileNameList);
        mAdapterView.setAdapter(mAdapter);
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case SCAN_OK:
                initAdapter();
                break;
            default:
                break;
        }
        return false;
    }
}
