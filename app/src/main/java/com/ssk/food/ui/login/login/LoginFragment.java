package com.ssk.food.ui.login.login;

import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.ui.login.forgetpwd.ForgetPwdActivity;
import com.ssk.food.ui.main.MainActivity;
import com.ssk.food.utils.GetCodeManage;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.di.module.AcitvityModule;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者:
 * 描述:Main
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.ContView, GetCodeManage.GetCodeListener {

    @BindView(R.id.rl_login)
    RelativeLayout rlLogin;
    @BindView(R.id.fl_login)
    FrameLayout flLogin;
    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.rl_login_del)
    RelativeLayout rlLoginDel;
    @BindView(R.id.iv_login_show)
    ImageView ivLoginShow;
    @BindView(R.id.rl_login_show)
    RelativeLayout rlLoginShow;
    @BindView(R.id.tv_login_switch)
    TextView tvLoginSwitch;

    GetCodeManage getCodeManage;
    private View view;
    private boolean isPhoneLogin;

    private boolean pswVisible = false; // 密码是否可见

    @Override
    public int initResource() {
        return R.layout.fragment_login;
    }

    @Override
    public void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(BaseApp.getInstance().getAppComponent())
                .acitvityModule(new AcitvityModule(getActivity()))
                .requestActionModule(new RequestActionModule(new RequestAction()))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {
        getCodeManage = new GetCodeManage(getActivity(), this);
        view = getCodeManage.getView();
        flLogin.addView(view);
    }

    @Override
    public void delPwd() {
        etLoginPwd.setText("");
    }

    @Override
    public void showPwd() {
        if (pswVisible) {
            pswVisible = false;
            ivLoginShow.setImageResource(R.mipmap.ic_pwd_unshow);
            etLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            pswVisible = true;
            ivLoginShow.setImageResource(R.mipmap.ic_pwd_show);
            etLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        etLoginPwd.setSelection(etLoginPwd.getText().toString().length());
    }

    @Override
    public void switchAccountLogin() {
        isPhoneLogin = false;
        rlLogin.setVisibility(View.VISIBLE);
        flLogin.setVisibility(View.GONE);
    }

    @Override
    public void switchPhoneLogin() {
        isPhoneLogin = true;
        rlLogin.setVisibility(View.GONE);
        flLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void frogetPwd() {
        startActivity(new Intent(getActivity(), ForgetPwdActivity.class));
    }

    @Override
    public void login() {
        if (!isPhoneLogin) {
            String account = etLoginAccount.getText().toString().trim();
            String pwd = etLoginPwd.getText().toString().trim();
            if (TextUtils.isEmpty(account)) {
                mToast.showToast(getString(R.string.login_account_hint));
            } else if (account.length() < 6) {
                mToast.showToast(getString(R.string.login_account_toast));
            } else if (TextUtils.isEmpty(pwd)) {
                mToast.showToast(getString(R.string.login_pwd_hint));
            } else {
                mPresenter.requestAccountLogin(account, pwd);
            }
        } else {
            getCodeManage.next();
        }
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @OnClick({R.id.rl_login_del, R.id.rl_login_show, R.id.tv_login_switch, R.id.tv_login_forget_pwd, R.id.btn_login_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login_del:
                delPwd();
                break;
            case R.id.rl_login_show:
                showPwd();
                break;
            case R.id.tv_login_switch:
                if (!isPhoneLogin) {
                    switchPhoneLogin();
                } else {
                    switchAccountLogin();
                }
                break;
            case R.id.tv_login_forget_pwd:
                frogetPwd();
                break;
            case R.id.btn_login_login:
                login();
                break;
        }
    }

    @Override
    public void getCode(String phone) {
        mPresenter.requestGetCode(phone);
    }

    @Override
    public void next(String phone, String code) {
        mPresenter.requestPhoneLogin(phone, code);
    }
}
