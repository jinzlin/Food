package com.ssk.food.ui.main;

import com.ssk.food.server.RequestAction;
import com.zhtx.mindlib.base.BasePresenter;

import javax.inject.Inject;

/**
 * 作者:Coding Farmer_5199
 * 描述:
 */

public class MainPresenter extends BasePresenter<MainContract.ContView> implements MainContract.ContPresenter<MainContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    MainPresenter() {
    }
}
