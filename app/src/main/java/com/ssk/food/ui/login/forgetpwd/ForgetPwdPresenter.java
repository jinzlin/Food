package com.ssk.food.ui.login.forgetpwd;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import static com.ssk.food.server.RequestAction.TAG_FORGET_PWD;
import static com.ssk.food.server.RequestAction.TAG_FORGET_PWD_NEXT;
import static com.ssk.food.server.RequestAction.TAG_GET_FORGET_PWD_CODE;

/**
 * 作者:
 * 描述:忘记密码
 */

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdContract.ContView> implements ForgetPwdContract.ContPresenter<ForgetPwdContract.ContView> {

    @Inject
    RequestAction requestAction;
    private String phone;

    @Inject
    ForgetPwdPresenter() {
    }

    @Override
    public void requestGetCode(String phone) {
        addSubscribe(requestAction.getForgetPwdCode(mRetrofitHelper, this, phone));
    }

    @Override
    public void requestNext(String phone, String code) {
        this.phone = phone;
        addSubscribe(requestAction.forgetPwdNext(mRetrofitHelper, this, phone, code));
    }

    @Override
    public void requestComplete(String phone, String pwd) {
        addSubscribe(requestAction.forgetPwd(mRetrofitHelper, this, phone, pwd));
    }

    @Override
    public void onSuccess2(int tag, boolean isRefreshLoad, JSONObject response) throws JSONException {
        super.onSuccess2(tag, isRefreshLoad, response);
        switch (tag) {
            case TAG_GET_FORGET_PWD_CODE:
                mView.getCodeSuccess();
                break;
            case TAG_FORGET_PWD_NEXT:
                mView.nextSuccess(phone);
                break;
            case TAG_FORGET_PWD:
                mView.submitSuccess();
                break;
        }
    }
}
