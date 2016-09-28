package com.github.xzwj87.mineflea.data.repository;

import com.github.xzwj87.mineflea.data.RepoResponseCode;
import com.github.xzwj87.mineflea.model.GoodsModel;

import java.util.List;

import rx.Observable;

/**
 * Created by jason on 9/26/16.
 */

public interface GoodsRepository {

    /**
     * publish a goods
     */
    Observable<RepoResponseCode> publishGoods(GoodsModel goods);

    /**
     * get a published goods detail by its id
     */
    Observable<GoodsModel> getPublishedGoodsDetail(long id);

    /**
     * get published goods list
     */
    Observable<List<GoodsModel>> getPublishedGoodsList();

    /**
     * get latest goods list
     */
    Observable<List<GoodsModel>> getLatestGoodsList();

    /**
     * favor a goods
     */
    Observable<RepoResponseCode> favorGoods(GoodsModel goods);

    /**
     * get favored goods detail
     */
    Observable<GoodsModel> getFavorGoodsDetail(long id);

    /**
     * get favored goods list
     */
    Observable<List<GoodsModel>> getFavorGoodsList();
}
