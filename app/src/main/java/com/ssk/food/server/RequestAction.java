package com.ssk.food.server;

import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.network.HttpCallBack;
import com.zhtx.mindlib.network.RetrofitHelper;
import com.zhtx.mindlib.utils.EnDecryptUtlis;
import com.zhtx.mindlib.utils.JsonUtils;
import com.zhtx.mindlib.utils.L;
import com.zhtx.mindlib.utils.MD5Utlis;
import com.zhtx.mindlib.utils.MSharedPreferences;
import com.zhtx.mindlib.utils.MToast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 作者: ljz.
 * @date 2018/6/6.
 * 描述：
 */

public class RequestAction {


    private MSharedPreferences mSharedPreferences = BaseApp.getInstance().getAppComponent().getMSharedPreferences();
    private MToast mToast = BaseApp.getInstance().getAppComponent().getMToast();

    public RequestAction() {
    }


    private static Map<String, String> getParams(String func, HashMap<String, Object> hashMap) {
        String randomCode = MD5Utlis.Md5(func);
        Map<String, String> map = new HashMap<>();
        map.put("func", func);
        map.put("words", randomCode + EnDecryptUtlis.aesEncrypt(JsonUtils.createJSON(hashMap).toString(), randomCode));

        for (Object key : hashMap.keySet()) {
            L.e(func + "----------requestTag", key + "=" + hashMap.get(key));
        }
        L.e("funcwords", map.toString());
        return map;
    }


    /**
     * 账号密码登录
     */
    public final static int TAG_ACCOUNT_LOGIN = 0;

    public Disposable accountLogin(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String account, String pwd) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("登录方式", "账号登录");
        map.put("账号", account);
        map.put("密码", MD5Utlis.Md5(pwd));
        return retrofitHelper.getDisposable(TAG_ACCOUNT_LOGIN, getParams("denglu", map), httpCallBack, true);
    }

    /**
     * 手机验证码登录
     */
    public final static int TAG_PHONE_LOGIN = 1;

    public Disposable phoneLogin(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("登录方式", "手机号登录");
        map.put("手机号", phone);
        map.put("验证码", code);
        return retrofitHelper.getDisposable(TAG_PHONE_LOGIN, getParams("denglu", map), httpCallBack, true);
    }

    /**
     * 获取登录验证码
     */
    public final static int TAG_GET_LOGIN_CODE = 2;

    public Disposable getLoginCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone) {
        return getCode(retrofitHelper, httpCallBack, TAG_GET_REGISTER_CODE, phone, "登录验证码");
    }

    /**
     * 获取注册验证码
     */
    public final static int TAG_GET_REGISTER_CODE = 3;

    public Disposable getRegisterCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone) {
        return getCode(retrofitHelper, httpCallBack, TAG_GET_REGISTER_CODE, phone, "注册验证码");
    }

    /**
     * 获取忘记登录密码验证码
     */
    public final static int TAG_GET_FORGET_PWD_CODE = 3;

    public Disposable getForgetPwdCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone) {
        return getCode(retrofitHelper, httpCallBack, TAG_GET_FORGET_PWD_CODE, phone, "忘记登录验证码");
    }

    private Disposable getCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, int tag, String phone, String type) {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("手机号", phone);
        map.put("类别", type);
        return retrofitHelper.getDisposable(tag, getParams("sendsms", map), httpCallBack, true);
    }



    /**
     * 验证注册验证码
     */
    public final static int TAG_REGISTER_NEXT = 4;

    public Disposable registerNext(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String code) {
        return smsCode(retrofitHelper, httpCallBack, TAG_REGISTER_NEXT, phone, "注册验证码", code);
    }
    /**
     * 验证忘记登录密码验证码
     */
    public final static int TAG_FORGET_PWD_NEXT = 4;

    public Disposable forgetPwdNext(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String code) {
        return smsCode(retrofitHelper, httpCallBack, TAG_FORGET_PWD_NEXT, phone, "忘记登录验证码", code);
    }

    private Disposable smsCode(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, int tag, String phone, String type, String code) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("手机号", phone);
        map.put("类别", type);
        map.put("验证码", code);
        return retrofitHelper.getDisposable(tag, getParams("smsCode", map), httpCallBack, true);
    }

    /**
     * 注册
     */
    public final static int TAG_REGISTER = 5;

    public Disposable register(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String account, String pwd) {
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("手机号", phone);
        map.put("账号", account);
        map.put("密码", MD5Utlis.Md5(pwd));
        map.put("确认密码", MD5Utlis.Md5(pwd));
        return retrofitHelper.getDisposable(TAG_REGISTER, getParams("zhuce", map), httpCallBack, true);
    }

    /**
     * 注册
     */
    public final static int TAG_FORGET_PWD = 5;

    public Disposable forgetPwd(RetrofitHelper retrofitHelper, HttpCallBack httpCallBack, String phone, String pwd) {
        HashMap<String, Object> map = new HashMap<>(3);
        map.put("手机号", phone);
        map.put("新密码", MD5Utlis.Md5(pwd));
        map.put("确认密码", MD5Utlis.Md5(pwd));
        return retrofitHelper.getDisposable(TAG_FORGET_PWD, getParams("b_update_pws", map), httpCallBack, true);
    }



}
