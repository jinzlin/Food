package com.ssk.food.utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ssk.food.App;
import com.ssk.food.R;
import com.zhtx.mindlib.utils.MToast;

/**
 * Author by ljz
 * PS:手机获取验证码管理类（手机验证码登录/注册/登录页忘记密码/ 密码管理忘记登录密码/密码管理忘记支付密码）
 */
public class GetCodeManage {

    private View view;
    private EditText etRegisterPhone;           // 手机号
    private EditText etRegisterCode;            // 验证码
    private TextView tvRegisterGetcode;         // 倒计时
    private RelativeLayout rlRegisterGetcode;   // 获取验证码点击

    private int currentNum; //倒计时秒数
    private Context context;
    private GetCodeListener getCodeListener;
    private MToast mToast = App.getInstance().getAppComponent().getMToast();


    public GetCodeManage(Context context, GetCodeListener getCodeListener) {
        this.context = context;
        this.getCodeListener = getCodeListener;
        initView();
    }

    private void initView() {
        view = ((Activity)context).getLayoutInflater().inflate(R.layout.fragment_getcode, null);
        etRegisterPhone = view.findViewById(R.id.et_getcode_phone);
        etRegisterCode = view.findViewById(R.id.et_getcode_code);
        tvRegisterGetcode = view.findViewById(R.id.tv_getcode_getcode);
        rlRegisterGetcode = view.findViewById(R.id.rl_getcode_get_code);

        rlRegisterGetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etRegisterPhone.getText().toString().trim();
                if (phone.length() < 11) {
                    mToast.showToast(context.getString(R.string.phone_toast));
                } else {
                    countDownTimer.start();
                    getCodeListener.getCode(phone);
                }
            }
        });
    }

    public View getView() {
        return view;
    }

    public void next(){
        String phone = etRegisterPhone.getText().toString();
        String code = etRegisterCode.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            mToast.showToast(context.getString(R.string.phone_hint));
        } else if (phone.length() < 11) {
            mToast.showToast(context.getString(R.string.phone_toast));
        } else if (TextUtils.isEmpty(code)) {
            mToast.showToast(context.getString(R.string.code_hint));
        } else {
            getCodeListener.next(phone, code);
        }
    }

    /**
     * 设置60秒倒计时
     */
    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            currentNum = (int) (millisUntilFinished / 1000);
            tvRegisterGetcode.setText(String.format(context.getString(R.string.get_code_count_down), currentNum));
        }

        @Override
        public void onFinish() {
            tvRegisterGetcode.setText(R.string.get_code);
            rlRegisterGetcode.setClickable(true);
        }
    };

    public interface GetCodeListener{

        void getCode(String phone);

        void next(String phone, String code);

    }
}
