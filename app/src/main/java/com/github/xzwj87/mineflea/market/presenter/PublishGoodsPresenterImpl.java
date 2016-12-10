package com.github.xzwj87.mineflea.market.presenter;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.github.xzwj87.mineflea.market.data.ResponseCode;
import com.github.xzwj87.mineflea.market.data.repository.DataRepository;
import com.github.xzwj87.mineflea.market.internal.di.PerActivity;
import com.github.xzwj87.mineflea.market.model.PublishGoodsInfo;
import com.github.xzwj87.mineflea.market.ui.BaseView;
import com.github.xzwj87.mineflea.market.ui.PublishGoodsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jason on 9/27/16.
 */

@PerActivity
public class PublishGoodsPresenterImpl extends PublishGoodsPresenter{
    public static final String TAG = PublishGoodsPresenterImpl.class.getSimpleName();

    @Inject
    DataRepository mRepository;
    private PublishGoodsView mView;
    private PublishGoodsInfo mGoodsInfo;

    private List<String> mImgUris;

    @Inject
    public PublishGoodsPresenterImpl(DataRepository repository){
        mRepository = repository;
    }

    @Override
    public void setView(BaseView view){
        mView = (PublishGoodsView)view;
    }

    @Override
    public void init() {
        Log.v(TAG,"init()");

        mGoodsInfo = new PublishGoodsInfo();
        mRepository.init();
        //mRepository.setPresenterCallback(this);
        mRepository.registerCallBack(PRESENTER_PUBLISH,new PublishPresenterCallback());
    }

    @Override
    public void publishGoods() {
        if(UserPrefsUtil.isLogin()) {
            mRepository.publishGoods(mGoodsInfo);
            mImgUris = mGoodsInfo.getImageUri();
        }else{
            mView.showNeedLoginMsg();
        }

        //mRepository.uploadImageById(mImgUris.get(0),false,true);
        //mRepository.uploadImage(mImgUris.get(mUploadImgCount),true);
    }

    @Override
    public void setGoodsName(String name) {
        mGoodsInfo.setName(name);
    }

    @Override
    public void setGoodsPrice(String price) {
        if(TextUtils.isEmpty(price)){
            mGoodsInfo.setPrice(0.0);
        }else {
            mGoodsInfo.setPrice(Double.parseDouble(price));
        }
    }

    @Override
    public void setGoodsNote(String note) {
        mGoodsInfo.setNote(note);
    }

    @Override
    public void setLocation(LatLng loc) {
        mGoodsInfo.setLocation(loc);
    }

    @Override
    public void setGoodsImgUrl(List<String> urls) {
        mGoodsInfo.setImageUri(urls);
        mImgUris = urls;
    }

    @Override
    public void setPublisherName(String name) {
        mGoodsInfo.setUserId(name);
    }

    @Override
    public boolean validGoodsInfo() {
        if(TextUtils.isEmpty(mGoodsInfo.getName())
            || mGoodsInfo.getName().length() > PublishGoodsInfo.MAX_NAME_SIZE){
            mView.showNameInvalidMsg();
            return false;
        }

        try {
            double price = mGoodsInfo.getPrice();
        }catch (NumberFormatException e){
            mView.showPriceInvalidMsg();
            return false;
        }

        if(mImgUris == null || mImgUris.size() <= 0){
            mView.showNoPicturesMsg();
            return false;
        }

        if(TextUtils.isEmpty(mGoodsInfo.getNote())){
            mGoodsInfo.setNote("");
            mView.showNoteInvalidMsg();
        }

        return true;
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        mGoodsInfo = null;
        mImgUris = null;
        mRepository.unregisterCallback(PRESENTER_PUBLISH);
    }

    private class PublishPresenterCallback implements PresenterCallback {

        @Override
        public void onComplete(Message message) {
            Log.v(TAG,"onComplete()");
            int what = message.what;
            switch (what){
                case ResponseCode.RESP_PUBLISH_GOODS_SUCCESS:
                    mRepository.uploadImages(mGoodsInfo.getImageUri(),false);
                    mGoodsInfo.setId(((PublishGoodsInfo)message.obj).getId());
                    mView.onPublishComplete(true);
                    break;
                case ResponseCode.RESP_PUBLISH_GOODS_ERROR:
                    mView.onPublishComplete(false);
                    break;
                case ResponseCode.RESP_NETWORK_NOT_CONNECTED:
                    mView.showNoNetConnectionMsg();
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onNext(Message message) {
            int count = (Integer)message.obj;
            Log.v(TAG,"onNext(): count = " + count);

            if(count == 100){
                mView.onPublishComplete(true);
                mView.finishView();
                return;
            }

            Log.v(TAG,"updateUploadProcess(): count = " + count);
            mView.updateUploadProcess(count);
        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
