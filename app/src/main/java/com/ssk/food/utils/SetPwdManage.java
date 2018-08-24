package com.ssk.food.utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ssk.food.App;
import com.ssk.food.R;
import com.zhtx.mindlib.utils.MToast;
import com.zhtx.mindlib.utils.StringUtil;

/**
 * Author by ljz
 * PS:设置密码管理类(注册/登录页忘记密码/密码管理忘记登录密码/密码管理忘记支付密码)
 */
public class SetPwdManage implements View.OnClickListener {

    private Context context;
    private SetPwdListener setPwdListener;
    private MToast mToast = App.getInstance().getAppComponent().getMToast();

    private View view;
    private EditText etSetPwd1;
    private EditText etSetPwd2;
    private ImageView ivSetPwdShow1;
    private ImageView ivSetPwdShow2;
    private RelativeLayout rlSetPwdShow1;
    private RelativeLayout rlSetPwdShow2;
    private RelativeLayout rlSetPwdDel;

    private boolean pswVisible1 = true; // 密码是否可见
    private boolean pswVisible2 = true; // 密码是否可见

    public SetPwdManage(Context context, SetPwdListener setPwdListener) {
        this.context = context;
        this.setPwdListener = setPwdListener;
        initView();
    }

    private void initView() {
        view = ((Activity) context).getLayoutInflater().inflate(R.layout.fragment_setpwd, null);
        etSetPwd1 = view.findViewById(R.id.et_set_pwd);
        etSetPwd2 = view.findViewById(R.id.et_set_pwd2);
        ivSetPwdShow1 = view.findViewById(R.id.iv_set_show);
        ivSetPwdShow2 = view.findViewById(R.id.iv_set_show2);
        rlSetPwdShow1 = view.findViewById(R.id.rl_set_show1);
        rlSetPwdShow2 = view.findViewById(R.id.rl_set_show2);
        rlSetPwdDel = view.findViewById(R.id.rl_set_del);
        rlSetPwdShow1.setOnClickListener(this);
        rlSetPwdShow2.setOnClickListener(this);
        rlSetPwdDel.setOnClickListener(this);
        initShow(pswVisible1, ivSetPwdShow1, etSetPwd1);
        initShow(pswVisible2, ivSetPwdShow2, etSetPwd2);

        etSetPwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    rlSetPwdDel.setVisibility(View.VISIBLE);
                }else {
                    rlSetPwdDel.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public View getView() {
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_set_show1:
                setShow(0, pswVisible1, ivSetPwdShow1, etSetPwd1);
                break;
            case R.id.rl_set_show2:
                setShow(1, pswVisible2, ivSetPwdShow2, etSetPwd2);
                break;
            case R.id.rl_set_del:
                etSetPwd2.setText("");
                break;
        }
    }

    public void complete() {
        String pwd = etSetPwd1.getText().toString().trim();
        String pwd2 = etSetPwd2.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            mToast.showToast(context.getString(R.string.set_pwd_toast));
        } else if (pwd.length() < 6) {
            mToast.showToast(context.getString(R.string.set_pwd_toast2));
        }  else if (!StringUtil.judgePasswordFormat(pwd)) {
            mToast.showToast(context.getString(R.string.set_pwd_toast3));
        } else if (TextUtils.isEmpty(pwd2)) {
            mToast.showToast(context.getString(R.string.set_pwd_toast0));
        } else if (!pwd.equals(pwd2)) {
            mToast.showToast(context.getString(R.string.set_pwd_toast1));
        } else {
            setPwdListener.complete(pwd);
        }
    }

    private void initShow(boolean isVisible, ImageView imageView, EditText editText) {
        if (isVisible) {
            imageView.setImageResource(R.mipmap.ic_pwd_show);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            imageView.setImageResource(R.mipmap.ic_pwd_unshow);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void setShow(int i, boolean isVisible, ImageView imageView, EditText editText) {
        if (isVisible) {
            if (i == 0) {
                pswVisible1 = false;
            } else {
                pswVisible2 = false;
            }
            imageView.setImageResource(R.mipmap.ic_pwd_unshow);
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            if (i == 0) {
                pswVisible1 = true;
            } else {
                pswVisible2 = true;
            }
            imageView.setImageResource(R.mipmap.ic_pwd_show);
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        editText.setSelection(editText.getText().toString().length());
    }

    public interface SetPwdListener {
        void complete(String pwd);
    }
}
