package com.ssk.food.ui.login.login;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.di.module.AcitvityModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者:
 * 描述:Main
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginContract.ContView {

    @BindView(R.id.tv_login_account)
    TextView tvLoginAccount;
    @BindView(R.id.et_login_account)
    EditText etLoginAccount;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.et_login_pwd)
    EditText etLoginPwd;
    @BindView(R.id.rl_login_get_code)
    RelativeLayout rlLoginGetCode;
    @BindView(R.id.rl_login_del)
    RelativeLayout rlLoginDel;
    @BindView(R.id.iv_login_show)
    ImageView ivLoginShow;
    @BindView(R.id.rl_login_show)
    RelativeLayout rlLoginShow;
    @BindView(R.id.tv_login_switch)
    TextView tvLoginSwitch;

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

    }

    @Override
    public void getCode() {
        String phone = etLoginAccount.getText().toString().trim();
        if (phone.length() < 11) {
            mToast.showToast(getString(R.string.login_phone_toast));
        } else {
            mPresenter.requestGetCode(phone);
        }
    }

    @Override
    public void delPwd() {
        etLoginPwd.setText("");
    }

    @Override
    public void showPwd() {
        if (pswVisible) {
            pswVisible = false;
            ivLoginShow.setImageResource(R.mipmap.ic_login_show);
            etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pswVisible = true;
            ivLoginShow.setImageResource(R.mipmap.ic_login_unshow);
            etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        etLoginPwd.setSelection(etLoginPwd.getText().toString().length());
    }

    @Override
    public void switchAccountLogin() {
        tvLoginAccount.setText(getString(R.string.login_account));
        etLoginAccount.setHint(getString(R.string.login_account_hint));
        etLoginAccount.setText("");
        tvLoginPwd.setText(getString(R.string.login_pwd));
        etLoginPwd.setHint(getString(R.string.login_pwd_hint));
        etLoginPwd.setText("");
        rlLoginGetCode.setVisibility(View.GONE);
        rlLoginDel.setVisibility(View.VISIBLE);
        rlLoginShow.setVisibility(View.VISIBLE);
        tvLoginSwitch.setText(getString(R.string.login_account_login));
    }

    @Override
    public void switchPhoneLogin() {
        tvLoginAccount.setText(getString(R.string.login_phone));
        etLoginAccount.setHint(getString(R.string.login_phone_hint));
        etLoginAccount.setText("");
        tvLoginPwd.setText(getString(R.string.login_code));
        etLoginPwd.setHint(getString(R.string.login_code_hint));
        etLoginPwd.setText("");
        rlLoginGetCode.setVisibility(View.VISIBLE);
        rlLoginDel.setVisibility(View.GONE);
        rlLoginShow.setVisibility(View.GONE);
        tvLoginSwitch.setText(getString(R.string.login_phone_login));
    }

    @Override
    public void frogetPwd() {

    }

    @Override
    public void login() {
        String account = etLoginAccount.getText().toString().trim();
        String pwd = etLoginPwd.getText().toString().trim();
        if (tvLoginAccount.getText().toString().equals("账号")) {
            if (TextUtils.isEmpty(account)) {
                mToast.showToast(getString(R.string.login_account_toast));
            } else if (account.length() < 6) {
                mToast.showToast(getString(R.string.login_account_toast));
            } else if (TextUtils.isEmpty(pwd)) {
                mToast.showToast(getString(R.string.login_pwd_hint));
            } else {
                mPresenter.requestAccountLogin(account, pwd);
            }
        } else {
            if (TextUtils.isEmpty(account)) {
                mToast.showToast(getString(R.string.login_phone_toast));
            } else if (account.length() < 11) {
                mToast.showToast(getString(R.string.login_phone_toast));
            } else if (TextUtils.isEmpty(pwd)) {
                mToast.showToast(getString(R.string.login_code_hint));
            } else {
                mPresenter.requestPhoneLogin(account, pwd);
            }
        }
    }

    @Override
    public void loginSuccess() {

    }

    @OnClick({R.id.rl_login_get_code, R.id.rl_login_del, R.id.rl_login_show, R.id.btn_login_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login_get_code:
                getCode();
                break;
            case R.id.rl_login_del:
                delPwd();
                break;
            case R.id.rl_login_show:
                showPwd();
                break;
            case R.id.btn_login_login:
                login();
                break;
        }
    }
}
