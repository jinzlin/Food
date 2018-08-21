package com.ssk.food.ui.order;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import javax.inject.Inject;

/**
 * 作者:
 * 描述:订单
 */

public class OrderPresenter extends BasePresenter<OrderContract.ContView> implements OrderContract.ContPresenter<OrderContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    OrderPresenter() {
    }

}
