package com.ssk.food.utils.dialog.customview;

import android.graphics.Color;
import android.view.View;

import com.ssk.food.R;
import com.ssk.food.utils.dialog.MDialogInterface;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.zhtx.mindlib.widge.MDialog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 作者: ljz.
 * @date 2018/5/21
 * 2级联动
 */

public class Custom2LinkageView {

    WheelView wheelView1;   // 1级联动控件
    WheelView wheelView2;   // 2级联动控件

    /**
     * @param mDialog dialog
     */
    public void builder(final MDialog mDialog, String title, final ArrayWheelAdapter arrayWheelAdapter1, final ArrayWheelAdapter arrayWheelAdapter2, final ArrayList<String> listData1, final HashMap<String, ArrayList<String>> listData2, final MDialogInterface.Linkage2Inter linkage2Inter) {
        mDialog.initDialog().withTitie(title)
                .setCustomView(R.layout.dialog_son_2linkage, new MDialog.CustomInter() {
                    @Override
                    public void custom(View customView) {
                        wheelView1 = (WheelView) customView.findViewById(R.id.main_wheelview);
                        wheelView2 = (WheelView) customView.findViewById(R.id.sub_wheelview);

                        wheelView1.setWheelAdapter(arrayWheelAdapter1);
                        wheelView1.setSkin(WheelView.Skin.Holo);
                        wheelView1.setWheelData(listData1);
                        wheelView2.setWheelAdapter(arrayWheelAdapter2);
                        wheelView2.setSkin(WheelView.Skin.Holo);
                        wheelView2.setWheelData(listData2.get(listData1.get(wheelView1.getSelection())));
                        wheelView1.join(wheelView2);
                        wheelView1.joinDatas(listData2);

                        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
                        style.selectedTextSize = 16;
                        style.textSize = 12;
                        style.holoBorderColor = Color.parseColor("#e5e5e5");
                        style.textColor = Color.GRAY;
                        wheelView1.setStyle(style);
                        wheelView2.setStyle(style);
                    }
                })
                .setBtn2(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String data1 = listData1.get(wheelView1.getCurrentPosition());
                        String data2 = listData2.get(data1).get(wheelView2.getCurrentPosition());
                        linkage2Inter.data(data1, data2, wheelView1.getCurrentPosition(), wheelView2.getCurrentPosition());
                        mDialog.dismiss();
                    }
                }).show();
    }
}
