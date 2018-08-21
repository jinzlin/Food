package com.ssk.food.ui.main;


import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:Coding Farmer_5199
 * 描述:
 */

public interface MainContract {

    interface ContView extends BaseContract.BaseView {

        /**
         * 初始化CommonTabLayout
         */
        void initCommonTabLayout();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

    }
}
