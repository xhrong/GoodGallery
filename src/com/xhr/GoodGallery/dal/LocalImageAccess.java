package com.xhr.GoodGallery.dal;

import com.xhr.GoodGallery.constant.GlobalConstants;
import com.xhr.GoodGallery.model.LocalPicInfo;
import com.xhr.GoodGallery.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xhrong on 2015/1/20.
 */
public class LocalImageAccess {

    private ArrayList<LocalPicInfo> localPicInfoLinkedList = new ArrayList<LocalPicInfo>();
    private int pageSize = 10;

    public LocalImageAccess(){
        loadImageList();
    }

    private void loadImageList() {
        String[] allImages = FileUtils.getFileNames(GlobalConstants.IMAGE_RES_PATH);
        for (String item : allImages) {
            LocalPicInfo localPicInfo = new LocalPicInfo();
            localPicInfo.picRawPath = GlobalConstants.IMAGE_RES_PATH + File.separator + item;
            localPicInfo.picDescription = item;
            localPicInfo.picName = item;
            localPicInfoLinkedList.add(localPicInfo);
        }
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public ArrayList<LocalPicInfo> loadImages(int pageIndex) {
        int startPos = pageIndex * pageSize;
        int endPos = startPos + pageSize >= localPicInfoLinkedList.size() ? localPicInfoLinkedList.size() - 1 : startPos + pageSize;
        if (startPos < 0 || startPos >= localPicInfoLinkedList.size()) {
            return null;
        }
        ArrayList<LocalPicInfo> temp = new ArrayList<LocalPicInfo>();
        for (int i = startPos;i<endPos;i++){
            temp.add(localPicInfoLinkedList.get(i));
        }
        return temp;
    }
}
