package com.ssk.food.ui.login.register;

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

    @Inject
    RegisterPresenter() {
    }

    @Override
    public void requestGetCode(String phone) {
        addSubscribe(requestAction.getRegisterCode(mRetrofitHelper, this, phone));
    }

    @Override
    public void requestNext(String phone, String code) {
        addSubscribe(requestAction.registerNext(mRetrofitHelper, this, phone, code));
    }

    @Override
    public void requestRegister(String account, String pwd) {
        addSubscribe(requestAction.register(mRetrofitHelper, this, account, pwd));

    }

    @Override
    public void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException {
        super.onSuccess2(tag, isRefreshLoad, response);
        switch (tag) {
            case TAG_GET_REGISTER_CODE:

                break;
            case TAG_REGISTER_NEXT:

                break;
            case TAG_REGISTER:

                break;
        }
    }
}
