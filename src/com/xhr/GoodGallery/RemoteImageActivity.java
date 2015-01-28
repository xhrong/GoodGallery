package com.xhr.GoodGallery;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huewu.pla.lib.XListView;
import com.xhr.GoodGallery.adapter.RemoteImageFlowAdapter;
import com.xhr.GoodGallery.model.RemotePicInfo;
import com.xhr.GoodGallery.utils.NetworkUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xhrong on 2015/1/23.
 */
public class RemoteImageActivity extends Fragment implements Handler.Callback {

    String TAG = "RemoteImageActivity";

    private final static int LOAD_IMAGE_SUCCESS = 0x005;

    private XListView mAdapterView = null;
    private RemoteImageFlowAdapter mAdapter;
    LinkedList<RemotePicInfo> remotePicInfoList = new LinkedList<RemotePicInfo>();

    int curPage = 0;

    ContentTask task;// = new ContentTask(this, 2);

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.remote_image);
//
//        mAdapterView = (XListView) findViewById(R.id.remote_image_list);
//        mAdapterView.setPullLoadEnable(true);
//        mAdapterView.setPullRefreshEnable(true);
//
//        mAdapterView.setXListViewListener(new XListView.IXListViewListener() {
//            @Override
//            public void onRefresh() {
//                AddItemToContainer(++curPage, 1);
//            }
//
//            @Override
//            public void onLoadMore() {
//                AddItemToContainer(++curPage, 2);
//            }
//        });
//        iniAdapter();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remote_image, container, false);

        task = new ContentTask(getActivity(),2);

        mAdapterView = (XListView) view.findViewById(R.id.remote_image_list);
        mAdapterView.setPullLoadEnable(true);
        mAdapterView.setPullRefreshEnable(true);

        mAdapterView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                AddItemToContainer(++curPage, 1);
            }

            @Override
            public void onLoadMore() {
                AddItemToContainer(++curPage, 2);
            }
        });
        iniAdapter();
        return view;

    }


    private void iniAdapter() {
        mAdapter = new RemoteImageFlowAdapter(getActivity(), remotePicInfoList);
        mAdapterView.setAdapter(mAdapter);
        AddItemToContainer(curPage, 2);
    }

    @Override
    public boolean handleMessage(Message message) {

        return false;
    }

    private class ContentTask extends AsyncTask<String, Integer, List<RemotePicInfo>> {

        private Context mContext;
        private int mType = 1;

        public ContentTask(Context context, int type) {
            super();
            mContext = context;
            mType = type;
        }

        @Override
        protected List<RemotePicInfo> doInBackground(String... params) {
            try {
                return parseNewsJSON(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RemotePicInfo> result) {
            if (mType == 1) {

                addItemTop(result);
                mAdapter.notifyDataSetChanged();
                mAdapterView.stopRefresh();

            } else if (mType == 2) {
                mAdapterView.stopLoadMore();
                addItemLast(result);
                mAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        public List<RemotePicInfo> parseNewsJSON(String url) throws IOException {
            List<RemotePicInfo> duitangs = new ArrayList<RemotePicInfo>();
            String json = "";
            if (NetworkUtils.checkConnection(mContext)) {
                try {
                    json = NetworkUtils.getStringFromUrl(url);

                } catch (IOException e) {
                    Log.e("IOException is : ", e.toString());
                    e.printStackTrace();
                    return duitangs;
                }
            }
            Log.d("MainActiivty", "json:" + json);

            try {
                if (null != json) {
                    JSONObject newsObject = new JSONObject(json);
                    JSONObject jsonObject = newsObject.getJSONObject("data");
                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");

                    for (int i = 0; i < blogsJson.length(); i++) {
                        JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                        RemotePicInfo newsInfo1 = new RemotePicInfo();

                        newsInfo1.picRawUrl = newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc");
                        newsInfo1.picDescription = newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg");
                        newsInfo1.picHeight = newsInfoLeftObject.getInt("iht");
                        newsInfo1.picWidth = newsInfoLeftObject.getInt("iwd");
                        duitangs.add(newsInfo1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return duitangs;
        }
    }

    /**
     * 添加内容
     *
     * @param pageindex
     * @param type      1为下拉刷新 2为加载更多
     */
    private void AddItemToContainer(int pageindex, int type) {
        if (task.getStatus() != AsyncTask.Status.RUNNING) {
            String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
            Log.d("MainActivity", "current url:" + url);
            ContentTask task = new ContentTask(getActivity(), type);
            task.execute(url);

        }
    }

    public void addItemLast(List<RemotePicInfo> datas) {
        remotePicInfoList.addAll(datas);
        GGApplication.setRemotePicInfoList(remotePicInfoList);
    }

    public void addItemTop(List<RemotePicInfo> datas) {
        for (RemotePicInfo info : datas) {
            remotePicInfoList.addFirst(info);
        }

        GGApplication.setRemotePicInfoList(remotePicInfoList);
    }
}
