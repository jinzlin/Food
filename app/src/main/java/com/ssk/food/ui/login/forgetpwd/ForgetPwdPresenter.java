package com.ssk.food.ui.login.forgetpwd;

import com.zhtx.mindlib.base.BasePresenter;
import com.ssk.food.server.RequestAction;

import javax.inject.Inject;

/**
 * 作者:
 * 描述:忘记密码
 */

public class ForgetPwdPresenter extends BasePresenter<ForgetPwdContract.ContView> implements ForgetPwdContract.ContPresenter<ForgetPwdContract.ContView> {

    @Inject
    RequestAction requestAction;

    @Inject
    ForgetPwdPresenter() {
    }

}
