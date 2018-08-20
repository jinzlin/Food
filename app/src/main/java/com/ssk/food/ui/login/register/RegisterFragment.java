package com.ssk.food.ui.login.register;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
 * 描述:注册
 */

public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.ContView {

    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.rl_register1)
    RelativeLayout rlRegister1;
    @BindView(R.id.et_register_account)
    EditText etRegisterAccount;
    @BindView(R.id.et_register_pwd)
    EditText etRegisterPwd;
    @BindView(R.id.iv_register_show)
    ImageView ivRegisterShow;
    @BindView(R.id.et_register_pwd2)
    EditText etRegisterPwd2;
    @BindView(R.id.rl_register_del)
    RelativeLayout rlRegisterDel;
    @BindView(R.id.iv_register_show2)
    ImageView ivRegisterShow2;
    @BindView(R.id.rl_register2)
    RelativeLayout rlRegister2;

    private boolean pswVisible1 = false; // 密码是否可见
    private boolean pswVisible2 = false; // 密码是否可见

    @Override
    public int initResource() {
        return R.layout.fragment_register;
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
        String phone = etRegisterPhone.getText().toString().trim();
        if (phone.length() < 11) {
            mToast.showToast(getString(R.string.register_phone_toast));
        } else {
            mPresenter.requestGetCode(phone);
        }
    }

    @Override
    public void next() {
        String phone = etRegisterPhone.getText().toString().trim();
        String code = etRegisterCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            mToast.showToast(getString(R.string.register_phone_toast));
        } else if (phone.length() < 11) {
            mToast.showToast(getString(R.string.register_phone_toast));
        } else if (TextUtils.isEmpty(code)) {
            mToast.showToast(getString(R.string.register_code_hint));
        } else {
            mPresenter.requestNext(phone, code);
        }
    }

    @Override
    public void nextSuccess() {
        rlRegister1.setVisibility(View.GONE);
        rlRegister2.setVisibility(View.VISIBLE);
    }

    @Override
    public void delPwd() {

    }

    @Override
    public void showPwd1() {
        if (pswVisible1) {
            pswVisible1 = false;
            ivRegisterShow.setImageResource(R.mipmap.ic_login_show);
            etRegisterPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pswVisible1 = true;
            ivRegisterShow.setImageResource(R.mipmap.ic_login_unshow);
            etRegisterPwd.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        etRegisterPwd.setSelection(etRegisterPwd.getText().toString().length());
    }

    @Override
    public void showPwd2() {
        if (pswVisible2) {
            pswVisible2 = false;
            ivRegisterShow2.setImageResource(R.mipmap.ic_login_show);
            etRegisterPwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pswVisible2 = true;
            ivRegisterShow2.setImageResource(R.mipmap.ic_login_unshow);
            etRegisterPwd2.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        etRegisterPwd2.setSelection(etRegisterPwd2.getText().toString().length());
    }

    @Override
    public void register() {
        String account = etRegisterAccount.getText().toString().trim();
        String pwd = etRegisterPwd.getText().toString().trim();
        String pwd2 = etRegisterPwd2.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            mToast.showToast(getString(R.string.register_account_toast));
        } else if (account.length() < 11) {
            mToast.showToast(getString(R.string.register_account_toast));
        } else if (TextUtils.isEmpty(pwd)) {
            mToast.showToast(getString(R.string.register_pwd_hint));
        } else if (TextUtils.isEmpty(pwd2)) {
            mToast.showToast(getString(R.string.register_pwd2_hint));
        } else if (!pwd.equals(pwd2)) {
            mToast.showToast(getString(R.string.register_pwd_toast));
        } else {
            mPresenter.requestRegister(account, pwd);
        }
    }

    @OnClick({R.id.rl_register_get_code, R.id.btn_register_next, R.id.rl_register_show1, R.id.rl_register_del, R.id.rl_register_show2, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_register_get_code:
                getCode();
                break;
            case R.id.btn_register_next:
                next();
                break;
            case R.id.rl_register_show1:
                showPwd1();
                break;
            case R.id.rl_register_del:
                delPwd();
                break;
            case R.id.rl_register_show2:
                showPwd2();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }
}
