package com.zhtx.mindlib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.widge.LoadDialog;
import com.zhtx.mindlib.utils.MToast;
import com.zhtx.mindlib.widge.MDialog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：BaseActivity
 */

public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends Fragment implements BaseContract.BaseView{

    @Inject
    protected T mPresenter;
    @Inject
    public MSharedPreferences mSharedPreferences;
    @Inject
    protected MToast mToast;
    protected Context mContext;
    protected Bundle savedInstanceState;
    protected RxPermissions rxPermissions;
    private LoadDialog loadDialog;
    protected MDialog mDialog;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mContext = getActivity();
        loadDialog = new LoadDialog(mContext);
        rxPermissions = new RxPermissions(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initResource(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initInject();
        initPresenter();
        initData();
        return view;
    }

    /**
     * 初始化布局资源文件
     */
    public abstract int initResource();

    /**
     * 依赖注入
     */
    public abstract void initInject();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * Presenter绑定View
     */
    private void initPresenter(){
        mPresenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showLoading() {
        loadDialog.show(mPresenter.getmCompositeDisposable());
    }

    @Override
    public void hideLoading() {
        loadDialog.dismiss();
    }

    @Override
    public MDialog getmDialog() {
        if (mDialog != null){
            return mDialog;
        } else {
            mDialog = new MDialog(getActivity());
        }
        return mDialog;
    }

    @Override
    public void dismissDialog(){
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        loadDialog.dismiss();
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter.unSubscribe();
        }
    }
}
