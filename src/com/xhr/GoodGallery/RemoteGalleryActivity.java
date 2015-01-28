package com.xhr.GoodGallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.diegocarloslima.byakugallery.lib.GalleryViewPager;
import com.xhr.GoodGallery.adapter.RemoteImageGalleryAdapter;
import com.xhr.GoodGallery.model.RemotePicInfo;
import com.xhr.GoodGallery.utils.StringUtils;

import java.util.List;

/**
 * Created by xhrong on 2015/1/20.
 */
public class RemoteGalleryActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.local_gallery);


        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imgpath");
        int index = intent.getIntExtra("index", 0);
        List<RemotePicInfo> list =GGApplication.getRemotePicInfoList();
        if (!StringUtils.isEmpty(imagePath)) {

        }


        final GalleryViewPager gallery = (GalleryViewPager) findViewById(R.id.gallery_view_pager_sample_gallery);
        gallery.setAdapter(new RemoteImageGalleryAdapter(getSupportFragmentManager(),list));
        gallery.setOffscreenPageLimit(1);
        gallery.setCurrentItem(index);
    }
}
