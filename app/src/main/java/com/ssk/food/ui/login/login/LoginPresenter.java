package com.ssk.food.ui.login.login;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import static com.ssk.food.server.RequestAction.TAG_ACCOUNT_LOGIN;
import static com.ssk.food.server.RequestAction.TAG_GET_LOGIN_CODE;
import static com.ssk.food.server.RequestAction.TAG_PHONE_LOGIN;

/**
 * 作者:
 * 描述:Main
 */

public class LoginPresenter extends BasePresenter<LoginContract.ContView> implements LoginContract.ContPresenter<LoginContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    LoginPresenter() {
    }

    @Override
    public void requestGetCode(String phone) {
        addSubscribe(requestAction.getLoginCode(mRetrofitHelper, this, phone));
    }

    @Override
    public void requestAccountLogin(String account, String pwd) {
        addSubscribe(requestAction.accountLogin(mRetrofitHelper, this, account, pwd));
    }

    @Override
    public void requestPhoneLogin(String phone, String code) {
        addSubscribe(requestAction.phoneLogin(mRetrofitHelper, this, phone, code));
    }

    @Override
    public void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException {
        super.onSuccess2(tag, isRefreshLoad, response);
        switch (tag) {
            case TAG_GET_LOGIN_CODE:

                break;
            case TAG_ACCOUNT_LOGIN:

                mView.loginSuccess();
                break;
            case TAG_PHONE_LOGIN:

                mView.loginSuccess();
                break;
        }
    }
}
