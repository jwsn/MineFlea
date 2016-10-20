package com.github.xzwj87.mineflea.market.presenter;

import android.os.Message;
import android.util.Log;

import com.github.xzwj87.mineflea.market.data.repository.MineFleaRepository;
import com.github.xzwj87.mineflea.market.internal.di.PerActivity;
import com.github.xzwj87.mineflea.market.model.PublishGoodsInfo;
import com.github.xzwj87.mineflea.market.ui.BaseView;
import com.github.xzwj87.mineflea.market.ui.PublishGoodsView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by jason on 9/27/16.
 */

@PerActivity
public class PublishGoodsPresenterImpl extends PublishGoodsPresenter{
    public static final String TAG = PublishGoodsPresenterImpl.class.getSimpleName();

    private MineFleaRepository mRepository;
    private PublishGoodsView mView;
    private PublishGoodsInfo mGoodsInfo;

    private int mUploadImgCount;
    private int mCurrentProcess;
    private static int sImgNumber;
    private List<String> mImgUris;

    @Inject
    public PublishGoodsPresenterImpl(@Named("dataRepository") MineFleaRepository repository){
        mRepository = repository;
    }

    @Override
    public void setView(BaseView view){
        mView = (PublishGoodsView)view;
    }

    @Override
    public void init() {
        mGoodsInfo = new PublishGoodsInfo();
        mRepository.init();
        mRepository.setPresenterCallback(this);

        mCurrentProcess = 0;
        mUploadImgCount = 0;
    }

    @Override
    public void publishGoods() {

        mRepository.publishGoods(mGoodsInfo);
        sImgNumber = mGoodsInfo.getImageUri().size();
        mImgUris = mGoodsInfo.getImageUri();
        mUploadImgCount = 0;
        mCurrentProcess = 0;
        mRepository.uploadImage(mImgUris.get(mUploadImgCount),true);
    }

    @Override
    public void setGoodsName(String name) {
        mGoodsInfo.setName(name);
    }

    @Override
    public void setGoodsLowPrice(double price) {
        mGoodsInfo.setLowerPrice(price);
    }

    @Override
    public void setGoodsHighPrice(double price) {
        mGoodsInfo.setHighPrice(price);
    }

    @Override
    public void setGoodsNote(String note) {
        mGoodsInfo.setNote(note);
    }

    @Override
    public void setLocation(String loc) {
        mGoodsInfo.setLocation(loc);
    }

    @Override
    public void setGoodsImgUrl(List<String> urls) {
        mGoodsInfo.setImageUri(urls);
    }

    @Override
    public void setPublisherName(String name) {
        mGoodsInfo.setPublisher(name);
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
        mGoodsInfo = null;
    }

    @Override
    public void onPublishComplete(Message message) {
        if(message.obj == null){
            mView.onPublishComplete(false);
        }else{
            mView.onPublishComplete(true);
        }
    }

    @Override
    public void updateUploadProcess(int count) {
        if((count == 100) && (++mUploadImgCount < sImgNumber)){
            mRepository.uploadImage(mImgUris.get(mUploadImgCount),true);
        }else if(mUploadImgCount == sImgNumber){
            mView.onPublishComplete(true);
            mUploadImgCount = 0;
            mCurrentProcess = 0;
            return;
        }

        mCurrentProcess = (count/sImgNumber) + mUploadImgCount*100/sImgNumber;
        Log.v(TAG,"updateUploadProcess(): count = " + mCurrentProcess);
        mView.updateUploadProcess(mCurrentProcess);
    }
}
