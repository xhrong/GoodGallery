package com.xhr.GoodGallery;

import android.app.Application;
import com.xhr.GoodGallery.model.LocalPicInfo;
import com.xhr.GoodGallery.model.RemotePicInfo;

import java.util.List;

/**
 * Created by xhrong on 2015/1/16.
 */
public class GGApplication extends Application {

    private static List<LocalPicInfo> localPicInfoList;

    private static List<RemotePicInfo> remotePicInfoList;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setLocalPicInfoList(List<LocalPicInfo> list){
      localPicInfoList=list;
    }

    public static List<LocalPicInfo> getLocalPicInfoList(){
        return localPicInfoList;
    }

    public static void setRemotePicInfoList(List<RemotePicInfo> list){
        remotePicInfoList=list;
    }

    public static List<RemotePicInfo> getRemotePicInfoList(){
        return remotePicInfoList;
    }
}
