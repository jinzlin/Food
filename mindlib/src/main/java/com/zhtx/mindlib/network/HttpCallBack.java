package com.zhtx.mindlib.network;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建人： zengming on 2017/9/25.
 * 功能：请求数据的回调接口
 */

public interface HttpCallBack<T> {

    /**
     * 请求开始
     * @param tag  请求的标志
     * @param showLoad  是否显示Load
     */
    void onStart(int tag, boolean showLoad);

    /**
     * 请求异常、失败
     * @param tag  请求的标志
     * @param isRefreshLoad  是否刷新加载
     * @param throwable  异常
     * @param showLoad  是否显示Load
     */
    void onError(int tag, boolean isRefreshLoad, Throwable throwable, boolean showLoad);

    /**
     * 请求完成
     * @param tag  请求的标志
     * @param isRefreshLoad  是否刷新加载
     * @param showLoad  是否显示Load
     */
    void onComplete(int tag, boolean isRefreshLoad, boolean showLoad);

    /**
     * 请求成功
     * @param tag  请求的标志
     * @param isRefreshLoad  是否刷新加载
     * @param t 泛型数据
     */
    void onSuccess(int tag, boolean isRefreshLoad, T t);

    /**
     * 操作数据：状态！=随机码不正确
     * @param tag  请求的标志
     * @param isRefreshLoad  是否刷新加载
     * @param response JSONObject数据
     */
    void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException;

    void onOther(int tag, boolean isRefreshLoad, int code, String message, JSONObject detail) throws JSONException;
}
