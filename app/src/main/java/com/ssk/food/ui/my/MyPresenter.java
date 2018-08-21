package com.ssk.food.ui.my;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import javax.inject.Inject;

/**
 * 作者:
 * 描述:我的
 */

public class MyPresenter extends BasePresenter<MyContract.ContView> implements MyContract.ContPresenter<MyContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    MyPresenter() {
    }

}
