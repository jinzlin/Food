package com.ssk.food.utils.dialog;

import android.view.View;

import com.ssk.food.utils.dialog.customview.Custom1LinkageView;
import com.ssk.food.utils.dialog.customview.Custom2LinkageView;
import com.ssk.food.utils.dialog.customview.CustomDateView;
import com.ssk.food.utils.dialog.customview.CustomListView;
import com.ssk.food.utils.dialog.customview.CustomPwd;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.zhtx.mindlib.widge.MDialog;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ssk.food.utils.dialog.customview.CustomDateView.ORDER_BY_DESC;


/**
 * 作者: Coding Farmer_5199.
 * @date 2017/11/7
 */

public class DialogUtlis {

    public static void oneBtnNormal(MDialog mDialog, String content) {
        oneBtnNormal(mDialog, "提示", content, null, true, null);
    }


    public static void oneBtnNormal(MDialog mDialog, String content, String btntext) {
        oneBtnNormal(mDialog, "提示", content, btntext, true, null);
    }

    public static void oneBtnNormal(MDialog mDialog, String content, String btntext, View.OnClickListener onClickListener) {
        oneBtnNormal(mDialog, content, btntext, true, onClickListener);
    }

    public static void oneBtnNormal(MDialog mDialog, String content, View.OnClickListener onClickListener) {
        oneBtnNormal(mDialog, content, null, false, onClickListener);
    }

    public static void oneBtnNormal(MDialog mDialog, String content, boolean cancelable, View.OnClickListener onClickListener) {
        oneBtnNormal(mDialog, content, null, cancelable, onClickListener);
    }

    public static void oneBtnNormal(MDialog mDialog, String content, String btnText, boolean cancelable, View.OnClickListener onClickListener) {
        oneBtnNormal(mDialog, "提示", content, -100, btnText, cancelable, onClickListener);
    }

    public static void oneBtnNormal(MDialog mDialog, String title, String content, String btnText, boolean cancelable, View.OnClickListener onClickListener) {
        oneBtnNormal(mDialog, title, content, -100, btnText, cancelable, onClickListener);
    }

    public static void twoBtnNormal(MDialog mDialog, String content, View.OnClickListener onClickListener) {
        twoBtnNormal(mDialog, "提示", content, -100, true, null, null, onClickListener, null);
    }

    public static void twoBtnNormal(MDialog mDialog, String content, int gravity, View.OnClickListener onClickListener) {
        twoBtnNormal(mDialog, "提示", content, gravity, true, null, null, onClickListener, null);
    }

    public static void twoBtnNormal(MDialog mDialog, String content, String btn1, String btn2, View.OnClickListener onClickListener) {
        twoBtnNormal(mDialog, "提示", content, -100, true, btn1, btn2, onClickListener, null);
    }

    public static void twoBtnNormal(MDialog mDialog, String title, String content, boolean cancelable, String btn1, String btn2, View.OnClickListener onClickListener1, View.OnClickListener onClickListener2) {
        twoBtnNormal(mDialog, title, content, -100, cancelable, btn1, btn2, onClickListener1, onClickListener2);
    }


    public static void oneBtnNormal(MDialog mDialog, String title, String content, int gravity, String btnText, boolean cancelable, View.OnClickListener onClickListener) {
        new NormalOneBtn().builder(mDialog, title, content, gravity, btnText, cancelable, onClickListener);
    }

    public static void twoBtnNormal(MDialog mDialog, String title, String content, int gravity, boolean cancelable, String btn1, String btn2, View.OnClickListener onClickListener1, View.OnClickListener onClickListener2) {
        new NormalTowBtn().builder(mDialog, title, content, gravity, cancelable, btn1, btn2, onClickListener1, onClickListener2);
    }

    public static void customDateView(MDialog mDialog, MDialogInterface.DataInter dataInter) {
        customDateView(mDialog, dataInter, ORDER_BY_DESC);
    }

    public static void customDateView(MDialog mDialog, MDialogInterface.DataInter dataInter, int orderby) {
        new CustomDateView().builder(mDialog, dataInter, orderby);
    }


    public static void customListView(final MDialog mDialog, String title, final ArrayList<String> datas, final MDialogInterface.ListViewOnClickInter listViewDataInter) {
        new CustomListView().builder(mDialog, title, datas, listViewDataInter);
    }


    public static void customPwd(final MDialog mDialog, final String message, final boolean isPay, final MDialogInterface.PasswordInter passwordInter) {
        new CustomPwd().builder(mDialog, message, isPay, passwordInter);
    }


    public static void custom1LinkageView(MDialog mDialog, String title, ArrayWheelAdapter arrayWheelAdapter1, ArrayList<String> listData1, MDialogInterface.Linkage1Inter linkage1Inter) {
        new Custom1LinkageView().builder(mDialog, title, arrayWheelAdapter1, listData1, linkage1Inter);
    }

    public static void custom2LinkageView(MDialog mDialog, String title, ArrayWheelAdapter arrayWheelAdapter1, ArrayWheelAdapter arrayWheelAdapter2, ArrayList<String> listData1, HashMap<String, ArrayList<String>> listData2, MDialogInterface.Linkage2Inter linkage2Inter) {
        new Custom2LinkageView().builder(mDialog, title, arrayWheelAdapter1, arrayWheelAdapter2, listData1, listData2, linkage2Inter);
    }

}
