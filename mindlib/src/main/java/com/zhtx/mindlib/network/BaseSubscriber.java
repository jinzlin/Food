package com.zhtx.mindlib.network;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 作者: ljz.
 * @date 2017/11/17.
 * 描述：
 */

public class BaseSubscriber<T> implements Observer<T> {
    private HttpCallBack<T> mHttpCallBack;
    private int tag;
    private boolean isRefreshLoad;
    private boolean showDialog;

    public BaseSubscriber(HttpCallBack httpCallBack, int tag, boolean isRefreshLoad, boolean showDialog) {
        this.mHttpCallBack = httpCallBack;
        this.tag = tag;
        this.isRefreshLoad = isRefreshLoad;
        this.showDialog = showDialog;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mHttpCallBack.onStart(tag, showDialog);
    }

    @Override
    public void onNext(@NonNull T t) {
        Log.e("-------", "onNext"+t);
        mHttpCallBack.onSuccess(tag, isRefreshLoad, t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("-------", "onError"+e);
        mHttpCallBack.onError(tag, isRefreshLoad, e, showDialog);
    }

    @Override
    public void onComplete() {
        Log.e("-------", "onComplete");
        mHttpCallBack.onComplete(tag, isRefreshLoad, showDialog);
    }

}
