package com.ssk.food.ui.handled;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:待处理
 */

public interface HandledContract {

    interface ContView extends BaseContract.BaseView {

    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

    }
}
