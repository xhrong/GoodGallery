package com.xhr.GoodGallery.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.bitmapfun.util.ImageFetcher;
import com.diegocarloslima.byakugallery.lib.TileBitmapDrawable;
import com.diegocarloslima.byakugallery.lib.TouchImageView;
import com.xhr.GoodGallery.R;
import com.xhr.GoodGallery.model.RemotePicInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhrong on 2015/1/22.
 */
public class RemoteImageGalleryAdapter extends FragmentStatePagerAdapter {

    List<RemotePicInfo> remotePicInfos=new LinkedList<RemotePicInfo>();


   public RemoteImageGalleryAdapter(FragmentManager fm, List<RemotePicInfo> remotePicInfos) {
        super(fm);
       this.remotePicInfos=remotePicInfos;
    }

    @Override
    public int getCount() {
        return this.remotePicInfos.size();
    }

    @Override
    public Fragment getItem(int position) {
        return RemoteGalleryFragment.getInstance(this.remotePicInfos.get(position));
    }



    public static final class RemoteGalleryFragment extends Fragment {
       static ImageFetcher imageFetcher;

        public static RemoteGalleryFragment getInstance(RemotePicInfo remotePicInfo) {
            final RemoteGalleryFragment instance = new RemoteGalleryFragment();
            final Bundle params = new Bundle();
            params.putSerializable("data", remotePicInfo);
            instance.setArguments(params);


            return instance;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View v = inflater.inflate(R.layout.local_gallery_item, null);

            final TouchImageView image = (TouchImageView) v.findViewById(R.id.gallery_view_pager_sample_item_image);
            final RemotePicInfo remotePicInfo =(RemotePicInfo) getArguments().getSerializable("data");


            final ProgressBar progress = (ProgressBar) v.findViewById(R.id.gallery_view_pager_sample_item_progress);


//            imageFetcher = new ImageFetcher(v.getContext(), 0);
//            imageFetcher.setLoadingImage(R.drawable.empty_photo);
//            imageFetcher.setNoImage(R.drawable.no_image);
//            imageFetcher.loadImage(remotePicInfo.picRawUrl,image);

            TileBitmapDrawable.attachTileBitmapDrawable( remotePicInfo.picRawUrl, image,null, new TileBitmapDrawable.OnInitializeListener() {

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
