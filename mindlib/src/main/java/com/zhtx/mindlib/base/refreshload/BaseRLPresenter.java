package com.zhtx.mindlib.base.refreshload;

import com.zhtx.mindlib.base.BasePresenter;
import com.zhtx.mindlib.base.bean.BaseRLBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: ljz.
 * 描述：刷新加载BaseRLPresenter
 */

public class BaseRLPresenter<B extends BaseRLBean, RL extends BaseRLContract.BaseRLView> extends BasePresenter<RL> implements BaseRLContract.BaseRLPresenter<RL>{

    public List<B> list = new ArrayList<>();

    @Override
    public void onComplete(int tag, boolean isRefreshLoad, boolean showLoad) {
        super.onComplete(tag, isRefreshLoad, showLoad);
    }

    @Override
    public void onError(int tag, boolean isRefreshLoad, Throwable throwable, boolean showLoad) {
        super.onError(tag, isRefreshLoad, throwable, showLoad);
        if (isRefreshLoad) {
            if (mView == null) {
                return;
            }
            if (mView.isRefresh()) {
                mView.refreshError();
            } else {
                mView.loadError();
            }
        }
    }

    @Override
    public void onOther(int tag, boolean isRefreshLoad, int code, String message, JSONObject detail) throws JSONException {
        super.onOther(tag, isRefreshLoad, code, message, detail);
        if (isRefreshLoad) {
            if (mView == null) {
                return;
            }
            if (mView.isRefresh()) {
                mView.refreshError();
            } else {
                mView.loadError();
            }
        }
        mToast.showToast(message);
    }

    @Override
    public void requestList(int page) {

    }
}
