package com.github.xzwj87.mineflea.market.ui.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.xzwj87.mineflea.R;
import com.github.xzwj87.mineflea.market.model.UserInfo;
import com.github.xzwj87.mineflea.market.presenter.UserDetailPresenterImpl;
import com.github.xzwj87.mineflea.market.ui.UserDetailView;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by jason on 10/22/16.
 */

public class UserDetailFragment extends BaseFragment implements UserDetailView{
    public static final String TAG = UserDetailFragment.class.getSimpleName();

    private CollapsingToolbarLayout mToolbarLayout;
    private AppBarLayout mAppBar;
    //private FloatingActionButton mFab;
    private ImageView mIvHeadIcon;
    private String mUserId;

    private boolean mIsCurrentUser;
    private ProgressDialog mProgressDlg;
/*    @BindView(R.id.tv_email) TextView mTvEmail;
    @BindView(R.id.tv_tel) TextView mTvTel;
    @BindView(R.id.tv_followers) TextView mTvFollowers;
    @BindView(R.id.tv_goods) TextView mTvGoods;*/

    private TextView mTvEmail;
    private TextView mTvTel;
    private TextView mTvFollowers;
    private TextView mTvGoods;

    private UserDetailPresenterImpl mPresenter;

    public UserDetailFragment(){}

    public static UserDetailFragment newInstance(String userId,boolean isCurrentUser){
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UserInfo.USER_ID,userId);
        bundle.putBoolean(UserInfo.CURRENT_USER,isCurrentUser);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedState){
        View root = inflater.inflate(R.layout.fragment_user_detail,container,false);

        //ButterKnife.bind(this,root);

        mTvEmail = (TextView)root.findViewById(R.id.tv_email);
        mTvTel = (TextView)root.findViewById(R.id.tv_tel);
        mTvFollowers = (TextView)root.findViewById(R.id.tv_followers);
        mTvGoods = (TextView)root.findViewById(R.id.tv_goods);

        mUserId = getArguments().getString(UserInfo.USER_ID);
        mIsCurrentUser = getArguments().getBoolean(UserInfo.CURRENT_USER,false);

        initView();
        init();

        return root;
    }

    @Override
    public void showLoadProcess(boolean stop) {
        if (stop && mProgressDlg.isShowing()) {
            mProgressDlg.dismiss();
        } else {
            mProgressDlg = ProgressDialog.show(getActivity(), "",
                    getString(R.string.get_user_detail_progress_hint));
        }
    }

    @Override
    public void showGetInfoFailMsg() {
        showToast(getString(R.string.error_get_user_info));
    }

    @Override
    public void renderHeadIcon(String iconUrl) {
/*        if(URLUtil.isNetworkUrl(iconUrl)){
            Picasso.with(getActivity())
                    .load(iconUrl)
                    .resize(1024,1024)
                    .centerCrop()
                    .into(mIvHeadIcon);
        }else{
            Picasso.with(getActivity())
                    .load(Uri.fromFile(new File(iconUrl)))
                    .resize(1024,1024)
                    .centerCrop()
                    .into(mIvHeadIcon);
        }*/
    }

    @Override
    public void renderNickName(String name) {
        //mToolbarLayout.setTitle(name);
    }

    @Override
    public void renderEmail(String email) {
        //mTvEmail.setText(email);
    }

    @Override
    public void updateActionButton(@ACTION_STATE int state) {

    }

    @Override
    public void finishView() {
        getActivity().finish();
    }

    private void init(){
        mPresenter.init();
        mPresenter.setView(this);

        getUserInfo();
    }

    private void getUserInfo(){
        mPresenter.getUserInfoById(mUserId);
    }

    private void initView(){
        mToolbarLayout = (CollapsingToolbarLayout)getActivity().
                findViewById(R.id.user_detail_toolbar_layout);
        mAppBar = (AppBarLayout)getActivity().
                findViewById(R.id.user_detail_appbar);
        mIvHeadIcon = (ImageView)getActivity().findViewById(R.id.head_icon);
    }


    private void getGoodsList(){
        mPresenter.getGoodsList(mUserId);
    }
}
