package com.ssk.food.ui.login.forgetpwd;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssk.food.App;
import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.utils.CommonUtils;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.di.module.AcitvityModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:
 * 描述:忘记密码
 */

public class ForgetPwdActivity extends BaseActivity<ForgetPwdPresenter> implements ForgetPwdContract.ContView {

    @BindView(R.id.et_forgetPwd_phone)
    EditText etForgetPwdPhone;
    @BindView(R.id.et_forgetPwd_code)
    EditText etForgetPwdCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.rl_forgetPwd1)
    RelativeLayout rlForgetPwd1;
    @BindView(R.id.et_forgetPwd_pwd)
    EditText etForgetPwdPwd;
    @BindView(R.id.iv_forgetPwd_show)
    ImageView ivForgetPwdShow;
    @BindView(R.id.et_forgetPwd_pwd2)
    EditText etForgetPwdPwd2;
    @BindView(R.id.rl_forgetPwd_del)
    RelativeLayout rlForgetPwdDel;
    @BindView(R.id.iv_forgetPwd_show2)
    ImageView ivForgetPwdShow2;
    @BindView(R.id.rl_forgetPwd2)
    RelativeLayout rlForgetPwd2;

    private boolean pswVisible1 = false; // 密码是否可见
    private boolean pswVisible2 = false; // 密码是否可见

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
    }

    @OnClick({R.id.iv_titlebar_back, R.id.rl_forgetPwd_get_code, R.id.btn_forgetPwd_next, R.id.rl_forgetPwd_show1, R.id.rl_forgetPwd_del, R.id.rl_forgetPwd_show2, R.id.btn_forgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.rl_forgetPwd_get_code:
                getCode();
                break;
            case R.id.btn_forgetPwd_next:
                next();
                break;
            case R.id.rl_forgetPwd_show1:
                showPwd1();
                break;
            case R.id.rl_forgetPwd_del:
                delPwd();
                break;
            case R.id.rl_forgetPwd_show2:
                showPwd2();
                break;
            case R.id.btn_forgetPwd:
                complete();
                break;
        }
    }

    @Override
    public void getCode() {
        String phone = etForgetPwdPhone.getText().toString().trim();
        if (phone.length() < 11) {
            mToast.showToast(getString(R.string.forgetPwd_phone_toast));
        } else {
            mPresenter.requestGetCode(phone);
        }
    }

    @Override
    public void next() {
        String phone = etForgetPwdPhone.getText().toString().trim();
        String code = etForgetPwdCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            mToast.showToast(getString(R.string.forgetPwd_phone_toast));
        } else if (phone.length() < 11) {
            mToast.showToast(getString(R.string.forgetPwd_phone_toast));
        } else if (TextUtils.isEmpty(code)) {
            mToast.showToast(getString(R.string.forgetPwd_code_hint));
        } else {
            mPresenter.requestNext(phone, code);
        }
    }

    @Override
    public void nextSuccess() {
        rlForgetPwd1.setVisibility(View.GONE);
        rlForgetPwd2.setVisibility(View.VISIBLE);
    }

    @Override
    public void delPwd() {

    }

    @Override
    public void showPwd1() {
        if (pswVisible1) {
            pswVisible1 = false;
            ivForgetPwdShow.setImageResource(R.mipmap.ic_login_show);
            etForgetPwdPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pswVisible1 = true;
            ivForgetPwdShow.setImageResource(R.mipmap.ic_login_unshow);
            etForgetPwdPwd.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        etForgetPwdPwd.setSelection(etForgetPwdPwd.getText().toString().length());
    }

    @Override
    public void showPwd2() {
        if (pswVisible2) {
            pswVisible2 = false;
            ivForgetPwdShow2.setImageResource(R.mipmap.ic_login_show);
            etForgetPwdPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pswVisible2 = true;
            ivForgetPwdShow2.setImageResource(R.mipmap.ic_login_unshow);
            etForgetPwdPwd2.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        etForgetPwdPwd2.setSelection(etForgetPwdPwd2.getText().toString().length());
    }

    @Override
    public void complete() {
        String pwd = etForgetPwdPwd.getText().toString().trim();
        String pwd2 = etForgetPwdPwd2.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            mToast.showToast(getString(R.string.forgetPwd_pwd_hint));
        } else if (TextUtils.isEmpty(pwd2)) {
            mToast.showToast(getString(R.string.forgetPwd_pwd2_hint));
        } else if (!pwd.equals(pwd2)) {
            mToast.showToast(getString(R.string.forgetPwd_pwd_toast));
        } else {
            mPresenter.requestComplete(pwd);
        }
    }
}
