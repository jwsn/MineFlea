package com.github.xzwj87.mineflea.market.model;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;

/**
 * Created by JasonWang on 2016/9/22.
 */
public class PublisherInfo {

    public static String USER_NAME = "user_name";

    public static String TOKEN = "token";

    private long mId;

    private String mToken;

    private String mName;

    private String mHeadIconUri;

    private String mTelNumber;

    private String mEmail;

    private int mCredit;

    private int mFollowers;

    private int mGoodsCount;

    private LatLng mLocation;

    private double mDistance;


    public PublisherInfo(){

    }

    public void setId(long id){
        mId = id;
    }

    public long getId(){
        return mId;
    }

    public void setName(String name){
        mName = name;
    }

    public String getName(){
        return mName;
    }

    public String getToken(){
        return mToken;
    }

    public void setToken(String token){
        mToken = token;
    }

    public String getHeadIconUri(){
        return mHeadIconUri;
    }

    public void setHeadIconUri(String iconUri){
        mHeadIconUri = iconUri;
    }

    public void setTelNumber(String no){
        mTelNumber = no;
    }

    public String getTelNumber(){
        return mTelNumber;
    }

    public void setEmail(String email){
        mEmail = email;
    }

    public String getEmail(){
        return mEmail;
    }

    public void setCredit(int credit){
        mCredit = credit;
    }

    public int getCredit(){
        return mCredit;
    }

    public void setFollowers(int followers){
        mFollowers = followers;
    }

    public int getFollowers(){
        return mFollowers;
    }

    public void setGoodsCount(int count){
        mGoodsCount = count;
    }

    public int getGoodsCount(){
        return mGoodsCount;
    }

    public void setLocation(LatLng location){
        mLocation = location;
    }

    public LatLng getLocation(){
        return mLocation;
    }

    public void setDistance(double distance){
        mDistance = distance;
    }

    public double getDistance(){
        return mDistance;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("-------- Publisher Detail --------\n");
        sb.append("id: " + mId + "\n");
        sb.append("name: " + mName + "\n");
        sb.append("email: " + mEmail + "\n");
        sb.append("credit: " + mCredit + "\n");
        sb.append("location: " + mLocation + "\n");
        sb.append("distance: " + mDistance + "\n");
        sb.append("----------------------------------\n");

        return sb.toString();
    }

}
