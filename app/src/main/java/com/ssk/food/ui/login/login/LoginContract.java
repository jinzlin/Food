package com.ssk.food.ui.login.login;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:Main
 */

public interface LoginContract {

    interface ContView extends BaseContract.BaseView {

        /**
         * 获取验证码
         */
        void getCode();

        /**
         * 清空密码
         */
        void delPwd();

        /**
         * 显示隐藏密码
         */
        void showPwd();

        /**
         * 账号密码登录
         */
        void switchAccountLogin();

        /**
         * 手机验证码登录
         */
        void switchPhoneLogin();

        /**
         * 忘记密码
         */
        void frogetPwd();

        /**
         * 登录
         */
        void login();

        /**
         * 登录成功
         */
        void loginSuccess();
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {
        void requestGetCode(String phone);
        void requestAccountLogin(String account, String pwd);
        void requestPhoneLogin(String phone, String code);
    }
}
