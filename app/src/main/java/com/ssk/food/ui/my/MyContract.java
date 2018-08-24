package com.ssk.food.ui.my;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:我的
 */

public interface MyContract {

    interface ContView extends BaseContract.BaseView {

        void initInfo();

        void isBranch();

        void isMain();

        void putForwary();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

    }
}
