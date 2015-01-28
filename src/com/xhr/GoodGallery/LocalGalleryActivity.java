package com.xhr.GoodGallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.diegocarloslima.byakugallery.lib.GalleryViewPager;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.xhr.GoodGallery.adapter.LocalImageGalleryAdapter;
import com.xhr.GoodGallery.model.LocalPicInfo;
import com.xhr.GoodGallery.utils.StringUtils;

import java.util.List;

/**
 * Created by xhrong on 2015/1/22.
 */
public class LocalGalleryActivity extends FragmentActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.local_gallery);


        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imgpath");
        int index = intent.getIntExtra("index", 0);
        List<LocalPicInfo> list =GGApplication.getLocalPicInfoList();
        if (!StringUtils.isEmpty(imagePath)) {

        }


        final GalleryViewPager gallery = (GalleryViewPager) findViewById(R.id.gallery_view_pager_sample_gallery);
        gallery.setAdapter(new LocalImageGalleryAdapter(getSupportFragmentManager(),list));
        gallery.setOffscreenPageLimit(1);
        gallery.setCurrentItem(index);
    }





    public static final class GalleryFragment extends Fragment {

        public static GalleryFragment getInstance(LocalPicInfo localPicInfo) {
            final GalleryFragment instance = new GalleryFragment();
            final Bundle params = new Bundle();
            params.putSerializable("data", localPicInfo);
            instance.setArguments(params);
            return instance;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.local_gallery_item, null);

            final TouchImageView image = (TouchImageView) v.findViewById(R.id.gallery_view_pager_sample_item_image);
            final LocalPicInfo localPicInfo =(LocalPicInfo) getArguments().getSerializable("data");
            //   final InputStream is = getResources().openRawResource(imageId);

            final ProgressBar progress = (ProgressBar) v.findViewById(R.id.gallery_view_pager_sample_item_progress);

            TileBitmapDrawable.attachTileBitmapDrawable(image, localPicInfo.picRawPath, null, new TileBitmapDrawable.OnInitializeListener() {

                @Override
                public void onStartInitialization() {
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onEndInitialization() {
                    progress.setVisibility(View.GONE);
                }
            });

            return v;
        }

    }
}
