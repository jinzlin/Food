package com.zhtx.mindlib.network;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: ljz.
 * @date 2017/11/17.
 * 描述：
 */

public class RetrofitHelper {

    private Disposable disposable;

    private final ApiService mApiService;

    public RetrofitHelper(ApiService apiService) {
        this.mApiService = apiService;
    }



    public Disposable getDisposable(int tag, Map<String, String> map, HttpCallBack httpCallBack, boolean showLoad) {
        return getDisposable(tag, false, map, httpCallBack, showLoad);
    }

    public Disposable getDisposable(int tag, boolean isRefreshLoad, Map<String, String> map, HttpCallBack httpCallBack, boolean showLoad) {
        mApiService.doPost(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<JSONObject>(httpCallBack, tag, isRefreshLoad, showLoad) {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        super.onSubscribe(d);
                        disposable = d;
                    }
                });
        return disposable;
    }
}
