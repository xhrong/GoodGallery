package com.xhr.GoodGallery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.xhr.GoodGallery.LocalGalleryActivity;
import com.xhr.GoodGallery.model.LocalPicInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhrong on 2015/1/22.
 */
public class LocalImageGalleryAdapter extends FragmentStatePagerAdapter {

    List<LocalPicInfo> localPicInfos=new LinkedList<LocalPicInfo>();

   public   LocalImageGalleryAdapter(FragmentManager fm,List<LocalPicInfo> localPicInfoList) {
        super(fm);
       localPicInfos=localPicInfoList;
    }

    @Override
    public int getCount() {
        return this.localPicInfos.size();
    }

    @Override
    public Fragment getItem(int position) {
        return LocalGalleryActivity.GalleryFragment.getInstance(this.localPicInfos.get(position));
    }
}
