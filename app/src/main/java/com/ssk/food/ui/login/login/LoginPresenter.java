package com.ssk.food.ui.login.login;

import com.google.gson.Gson;
import com.ssk.food.bean.LoginBean;
import com.ssk.food.utils.ConstantUtlis;
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
                mToast.showToast("获取验证码成功");
                break;
            case TAG_ACCOUNT_LOGIN:
            case TAG_PHONE_LOGIN:
                LoginBean loginBean = new Gson().fromJson(response.toString(), LoginBean.class);
                mSharedPreferences.setString(ConstantUtlis.SP_ACCOUNT, loginBean.getAccount());
                mSharedPreferences.setString(ConstantUtlis.SP_RANDOM, loginBean.getRandom());
                mSharedPreferences.setString(ConstantUtlis.SP_NIKE_NAME, loginBean.getNikeName());
                mSharedPreferences.setString(ConstantUtlis.SP_IS_MAIN_ACCOUNT, loginBean.getIsMainCount());
                mView.loginSuccess();
                break;
        }
    }


}
