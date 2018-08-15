package com.ssk.food.utils.dialog;

import android.view.View;

import com.zhtx.mindlib.widge.MDialog;


/**
 * 作者: Coding Farmer_5199.
 * @date 2017/11/7
 * 一个个按钮Dialog
 */

class NormalOneBtn {

    /**
     * @param mDialog         dialog
     * @param title           标题
     * @param content         内容
     * @param gravity         内容位置
     * @param btnText         按钮文字
     * @param cancelable      能否取消
     * @param onClickListener 点击事件
     */
    void builder(final MDialog mDialog, String title, String content, int gravity, String btnText, boolean cancelable, View.OnClickListener onClickListener) {
        mDialog.setCancelable(cancelable);
        mDialog.initDialog().withTitie(title);
        if (gravity != -100) {
            mDialog.withContene(content, gravity);
        } else {
            mDialog.withContene(content);
        }
        if (onClickListener == null) {
            mDialog.setBtn1(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            });
        } else {
            mDialog.setBtn1(onClickListener);
        }
        if (btnText != null)
            mDialog.setBtn1Text(btnText);
        mDialog.show();
    }
}
