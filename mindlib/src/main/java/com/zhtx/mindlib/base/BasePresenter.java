package com.zhtx.mindlib.base;


import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.zhtx.mindlib.network.HttpCallBack;
import com.zhtx.mindlib.network.RetrofitHelper;
import com.zhtx.mindlib.utils.BroadcastManager;
import com.zhtx.mindlib.utils.EventTag;
import com.zhtx.mindlib.utils.JsonUtils;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;
import com.zhy.autolayout.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者: ljz.
 * @date 2017/11/15
 */

public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T>, HttpCallBack<JSONObject> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    protected RetrofitHelper mRetrofitHelper;
    private int i;  // 网络请求个数，用于判断是否hideLoading

    @Inject
    protected MSharedPreferences mSharedPreferences;
    @Inject
    protected MToast mToast;


    @Override
    public CompositeDisposable getmCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean remove(Disposable disposable) {
        return mCompositeDisposable != null && mCompositeDisposable.remove(disposable);
    }

    @Override
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }


    @Override
    public void onStart(int tag, boolean showLoad) {
        if (!showLoad) {
            return;
        }
        i++;
        mView.showLoading();
    }

    @Override
    public void onError(int tag, boolean isRefreshLoad, Throwable throwable, boolean showLoad) {
        if (throwable instanceof ConnectException) {
            mToast.showToast("链接服务器超时");
        }
        if (throwable instanceof SocketTimeoutException) {
            mToast.showToast("网络请求超时");
        }
        if (!showLoad) {
            return;
        }
        i--;
        if (i == 0) {
            mView.hideLoading();
        }
    }

    @Override
    public void onComplete(int tag, boolean isRefreshLoad, boolean showLoad) {
        if (!showLoad) {
            return;
        }
        i--;
        if (i == 0) {
            mView.hideLoading();
        }
    }

    @Override
    public void onSuccess(int tag, boolean isRefreshLoad, JSONObject t) {
//        if (EventTag.TAG_EVENT_REQUEST_RANDOM_ERROR.equals(JsonUtils.getString(t, "message"))) {
//            if (mSharedPreferences.getBoolean("SP_IS_LOGIN", true)) {
//                mSharedPreferences.setBoolean("SP_IS_LOGIN", false);
//                BroadcastManager.getInstance(BaseApp.getInstance()).sendBroadcast(EventTag.TAG_EVENT_REQUEST, EventTag.TAG_EVENT_REQUEST_RANDOM_ERROR);
//            }
//            return;
//        }
        if ("成功".equals(JsonUtils.getString(t, "状态"))) {
            try {
                onSuccess2(tag, isRefreshLoad, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                onOther(tag, isRefreshLoad, 0, JsonUtils.getString(t, "状态"), t);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException {

    }

    @Override
    public void onOther(int tag, boolean isRefreshLoad, int code, String message, JSONObject detail) throws JSONException {
        mToast.showToast(message);
    }


}
