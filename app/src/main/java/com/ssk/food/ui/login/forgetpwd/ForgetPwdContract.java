package com.ssk.food.ui.login.forgetpwd;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:忘记密码
 */

public interface ForgetPwdContract {

    interface ContView extends BaseContract.BaseView {

        /**
         * 获取验证码成功
         */
        void getCodeSuccess();

        /**
         * 下一步成功
         */
        void nextSuccess(String phone);

        /**
         * 完成
         */
        void submit();

        /**
         * 设置成功
         */
        void submitSuccess();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

        void requestGetCode(String phone);

        void requestNext(String phone, String code);

        void requestComplete(String phone, String pwd);
    }
}
