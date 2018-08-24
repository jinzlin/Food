package com.ssk.food.ui.login.register;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.ssk.food.ui.main.MainActivity;
import com.ssk.food.utils.GetCodeManage;
import com.ssk.food.utils.SetPwdManage;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.di.module.AcitvityModule;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者:
 * 描述:注册
 */

public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterContract.ContView, GetCodeManage.GetCodeListener, SetPwdManage.SetPwdListener {


    @BindView(R.id.fl_register)
    FrameLayout flRegister;
    @BindView(R.id.btn_register_next)
    Button btnRegisterNext;
    @BindView(R.id.et_register_account)
    EditText etRegisterAccount;
    @BindView(R.id.rl_register_account)
    RelativeLayout rlRegisterAccount;

    private View view1;
    private View view2;
    private boolean isNext;
    private String phone;

    GetCodeManage getCodeManage;
    SetPwdManage setPwdManage;

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
        getCodeManage = new GetCodeManage(getActivity(), this);
        view1 = getCodeManage.getView();
        flRegister.addView(view1);
    }


    @Override
    public void getCodeSuccess() {
        mToast.showToast(getString(R.string.get_code_success));
    }

    @Override
    public void nextSuccess(String phone) {
        this.phone = phone;
        setPwdManage = new SetPwdManage(getActivity(), this);
        view2 = setPwdManage.getView();
        isNext = true;
        btnRegisterNext.setText(getString(R.string.complete));
        flRegister.removeView(view1);
        flRegister.addView(view2);
        rlRegisterAccount.setVisibility(View.VISIBLE);
    }


    @Override
    public void register() {
        String account = etRegisterAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            mToast.showToast(getString(R.string.register_account_toast2));
        } else if (account.length() < 6) {
            mToast.showToast(getString(R.string.register_account_toast));
        } else {
            setPwdManage.complete();
        }
    }

    @Override
    public void registerSuccess() {
        isNext = false;
        btnRegisterNext.setText(getString(R.string.next));
        flRegister.removeView(view2);
        flRegister.addView(view1);
        rlRegisterAccount.setVisibility(View.GONE);
        getActivity().finish();
        startActivity(new Intent(mContext, MainActivity.class));
    }


    @OnClick(R.id.btn_register_next)
    public void onViewClicked() {
        if (!isNext) {
            getCodeManage.next();
        } else {
            register();
        }
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
        mPresenter.requestRegister(phone, etRegisterAccount.getText().toString().trim(), pwd);
    }

}
