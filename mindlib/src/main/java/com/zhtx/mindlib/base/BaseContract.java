package com.zhtx.mindlib.base;

import android.content.Context;

import com.zhtx.mindlib.widge.MDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者: ljz.
 * @date 2017/11/15
 * 用来管理 presenter 与 view
 */

public interface BaseContract {

    interface BaseView{

        Context getContext();

        /**
         * 显示加载对话框
         */
        void showLoading();

        /**
         * 隐藏加载框
         */
        void hideLoading();

        /**
         * 获取dialog
         * @return MDialog
         */
        MDialog getmDialog();

        /**
         * 销毁dialog
         */
        void dismissDialog();
    }

    interface BasePresenter<T>{

        /**
         * 绑定view
         * @param view view
         */
        void attachView(T view);

        /**
         * 解绑view
         */
        void detachView();

        /**
         * 获取网络请求集合
         * @return CompositeDisposable
         */
        CompositeDisposable getmCompositeDisposable();

        /**
         * 加入网络请求集合
         * @param disposable 网络请求
         */
        void addSubscribe(Disposable disposable);

        /**
         * 删除网络请求
         * @param disposable 网络请求
         * @return 是否删除成功
         */
        boolean remove(Disposable disposable);

        /**
         * 清空网络请求集合
         */
        void unSubscribe();
    }
}
