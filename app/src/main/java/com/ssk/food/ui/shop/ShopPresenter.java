package com.ssk.food.ui.shop;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import javax.inject.Inject;

/**
 * 作者:
 * 描述:店铺
 */

public class ShopPresenter extends BasePresenter<ShopContract.ContView> implements ShopContract.ContPresenter<ShopContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    ShopPresenter() {
    }

    @Override
    public void requestShopData() {

    }
}
