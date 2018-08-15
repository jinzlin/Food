package com.ssk.food.utils.dialog.customview;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssk.food.R;
import com.ssk.food.utils.dialog.MDialogInterface;
import com.zhtx.mindlib.widge.MDialog;
import com.zhtx.mindlib.widge.PwdInputView;


/**
 * 作者: Coding Farmer_5199.
 * @date 2017/11/7
 * 输入密码框
 */

public class CustomPwd {

    /**
     * @param mDialog       dialog
     * @param message       显示内容
     * @param isPay         是否显示金额(true 文本字体会比较大)
     * @param passwordInter 密码回调
     */
    public void builder(final MDialog mDialog, final String message, final boolean isPay, final MDialogInterface.PasswordInter passwordInter) {
        mDialog.initDialog().setCustomView(R.layout.dialog_son_paypwd, new MDialog.CustomInter() {
            @Override
            public void custom(View customView) {
                final PwdInputView pv_paypwd = (PwdInputView) customView.findViewById(R.id.pv_paypwd);
                TextView tv_title = (TextView) customView.findViewById(R.id.tv_title);
                ImageView iv_close = (ImageView) customView.findViewById(R.id.iv_close);
                TextView tv_message = (TextView) customView.findViewById(R.id.tv_message);

                tv_title.setText("请输入支付密码");

                if (TextUtils.isEmpty(message)) {
                    tv_message.setVisibility(View.GONE);
                } else {
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(message);
                    if (isPay) {
                        tv_message.setTextSize(50);
                    } else {
                        tv_message.setTextSize(20);
                    }
                }
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                pv_paypwd.setDisplayPasswords(false); //设置是否显示密码
                pv_paypwd.setRadiusBg(0); //设置方框的圆角度数

                //设置监听内部输入的字符
                pv_paypwd.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.toString().length() == 6) {
                            passwordInter.callback(s.toString());
                            mDialog.dismiss();
                        }
                    }
                });
            }
        }).show();
    }
}
