package com.ssk.food.ui.login.register;

import android.util.Log;

import com.google.gson.Gson;
import com.ssk.food.R;
import com.ssk.food.bean.LoginBean;
import com.ssk.food.utils.ConstantUtlis;
import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import static com.ssk.food.server.RequestAction.TAG_GET_REGISTER_CODE;
import static com.ssk.food.server.RequestAction.TAG_REGISTER;
import static com.ssk.food.server.RequestAction.TAG_REGISTER_NEXT;

/**
 * 作者:
 * 描述:注册
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.ContView> implements RegisterContract.ContPresenter<RegisterContract.ContView> {

    @Inject
    RequestAction requestAction;
    private String phone;

    @Inject
    RegisterPresenter() {
    }

    @Override
    public void requestGetCode(String phone) {
        addSubscribe(requestAction.getRegisterCode(mRetrofitHelper, this, phone));
    }

    @Override
    public void requestNext(String phone, String code) {
        this.phone = phone;
        addSubscribe(requestAction.registerNext(mRetrofitHelper, this, phone, code));
    }

    @Override
    public void requestRegister(String phone, String account, String pwd) {
        addSubscribe(requestAction.register(mRetrofitHelper, this, phone, account, pwd));
    }

    @Override
    public void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException {
        super.onSuccess2(tag, isRefreshLoad, response);
        switch (tag) {
            case TAG_GET_REGISTER_CODE:
                mView.getCodeSuccess();
                break;
            case TAG_REGISTER_NEXT:
                mView.nextSuccess(phone);
                break;
            case TAG_REGISTER:
                LoginBean loginBean = new Gson().fromJson(response.toString(), LoginBean.class);
                mSharedPreferences.setString(ConstantUtlis.SP_ACCOUNT, loginBean.getAccount());
                mSharedPreferences.setString(ConstantUtlis.SP_RANDOM, loginBean.getRandom());
                mSharedPreferences.setString(ConstantUtlis.SP_NIKE_NAME, loginBean.getNikeName());
                mSharedPreferences.setString(ConstantUtlis.SP_IS_MAIN_ACCOUNT, loginBean.getIsMainCount());
                mView.registerSuccess();
                break;
        }
    }
}
