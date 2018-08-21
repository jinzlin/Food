package com.ssk.food.ui.handled;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import javax.inject.Inject;

/**
 * 作者:
 * 描述:待处理
 */

public class HandledPresenter extends BasePresenter<HandledContract.ContView> implements HandledContract.ContPresenter<HandledContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    HandledPresenter() {
    }

}
