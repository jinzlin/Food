package com.zhtx.mindlib.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.githang.statusbar.StatusBarCompat;
import com.zhtx.mindlib.R;
import com.zhtx.mindlib.network.RetrofitHelper;
import com.zhtx.mindlib.utils.L;
import com.zhtx.mindlib.widge.LoadDialog;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhtx.mindlib.widge.MDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


/**
 * 作者: ljz.
 * @date 2017/11/15
 * 描述：BaseActivity
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends AutoLayoutActivity implements BaseContract.BaseView{

    @Inject
    protected T mPresenter;
    @Inject
    protected RetrofitHelper mRetrofitHelper;
    @Inject
    public MSharedPreferences mSharedPreferences;
    @Inject
    protected MToast mToast;
    protected Context mContext;
    protected Bundle savedInstanceState;
    protected RxPermissions rxPermissions;
    private LoadDialog loadDialog;

    protected MDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.savedInstanceState = savedInstanceState;
        setContentView(initResource());
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        ButterKnife.bind(this);
        BaseApp.getInstance().mActivityStack.addActivity(this);
        mContext = this;
        loadDialog = new LoadDialog(this);
        rxPermissions = new RxPermissions(this);
        initInject();
        initPresenter();
        initData();
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
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showLoading() {
        if (null != mPresenter) {
            loadDialog.show(mPresenter.getmCompositeDisposable());
        } else {
            loadDialog.show(null);
        }
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
            mDialog = new MDialog(this);
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        if (null != mPresenter) {
            mPresenter.unSubscribe();
        }
        BaseApp.getInstance().mActivityStack.removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadDialog.dismiss();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        dismissDialog();
    }
}
