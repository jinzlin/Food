package com.ssk.food.ui.login.register;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:注册
 */

public interface RegisterContract {

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
         * 注册
         */
        void register();

        /**
         * 注册成功
         */
        void registerSuccess();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {

        void requestGetCode(String phone);

        void requestNext(String phone, String code);

        void requestRegister(String phone, String account, String pwd);
    }
}
