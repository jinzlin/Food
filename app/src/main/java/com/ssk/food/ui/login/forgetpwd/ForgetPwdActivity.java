package com.ssk.food.ui.login.forgetpwd;

import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ssk.food.App;
import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.utils.CommonUtils;
import com.ssk.food.utils.GetCodeManage;
import com.ssk.food.utils.SetPwdManage;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.di.module.AcitvityModule;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者:
 * 描述:忘记密码
 */

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.ContView, GetCodeManage.GetCodeListener, SetPwdManage.SetPwdListener {


    @BindView(R.id.view_status_bar)
    View viewStatusBar;
    @BindView(R.id.fl_forget_pwd)
    FrameLayout flForgetPwd;
    @BindView(R.id.btn_forgetPwd)
    Button btnForgetPwd;

    GetCodeManage getCodeManage;
    SetPwdManage setPwdManage;

    private View view1;
    private View view2;
    private boolean isNext;
    private String phone;

    @Override
    public int initResource() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(App.getInstance().getAppComponent())
                .acitvityModule(new AcitvityModule(this))
                .requestActionModule(new RequestActionModule(new RequestAction()))
                .build().inject(this);
    }

    @Override
    public void initData() {
        CommonUtils.setTransparentForWindow(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewStatusBar.setVisibility(View.VISIBLE);
        }
        getCodeManage = new GetCodeManage(mContext, this);
        view1 = getCodeManage.getView();
        flForgetPwd.addView(view1);
    }

    @OnClick({R.id.iv_titlebar_back, R.id.btn_forgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_forgetPwd:
                if (!isNext) {
                    getCodeManage.next();
                } else {
                    submit();
                }
                break;
        }
    }

    @Override
    public void getCodeSuccess() {
        mToast.showToast(getString(R.string.get_code_success));
    }

    @Override
    public void nextSuccess(String phone) {
        this.phone = phone;
        setPwdManage = new SetPwdManage(mContext, this);
        view2 = setPwdManage.getView();
        isNext = true;
        btnForgetPwd.setText(getString(R.string.complete));
        flForgetPwd.removeView(view1);
        flForgetPwd.addView(view2);
    }

    @Override
    public void submit() {
        setPwdManage.complete();
    }

    @Override
    public void submitSuccess() {
        mToast.showToast(getString(R.string.success));
        finish();
    }

    @Override
    public void getCode(String phone) {
        mPresenter.requestGetCode(phone);
    }

    @Override
    public void next(String phone, String code) {
        mPresenter.requestNext(phone, code);
    }

    @Override
    public void complete(String pwd) {
        mPresenter.requestComplete(phone, pwd);
    }
}
