package com.zhtx.mindlib.base.refreshload;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.zhtx.mindlib.base.BaseContract;
import com.zhtx.mindlib.base.bean.BaseRLBean;
import com.zhtx.mindlib.utils.MRefreshLoad;

import java.util.List;

/**
 * 作者: ljz.
 * 描述：刷新加载BaseRLContract
 */

public interface BaseRLContract {

    interface BaseRLView<T extends BaseRLBean> extends BaseContract.BaseView {

        /**
         * 是否正在刷新
         * @return true/正在属性
         */
        boolean isRefresh();

        /**
         * 刷新错误
         */
        void refreshError();

        /**
         * 加载错误
         */
        void loadError();

        /**
         * 刷新加载成功
         * @param list  数据集合
         * @param page  页数
         */
        void refreshLoadComplete(List<T> list, int page);

    }

    interface BaseRLPresenter<T> extends BaseContract.BasePresenter<T> {
        /**
         * 请求数据
         * @param page 页数
         */
        void requestList(int page);
    }
}
