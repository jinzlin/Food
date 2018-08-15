package com.ssk.food.utils.dialog.customview;

import android.view.View;
import android.widget.DatePicker;

import com.ssk.food.R;
import com.ssk.food.utils.dialog.MDialogInterface;
import com.zhtx.mindlib.utils.DatePrickerUtil;
import com.zhtx.mindlib.widge.MDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 作者: Coding Farmer_5199.
 * @date 2017/11/7
 * 日期选择
 */

public class CustomDateView {

    private DatePicker datePicker;  // 日期选择控件
    public static final int ORDER_BY_DESC = 0;  // 时间降序
    public static final int ORDER_BY_ASC = 1;   // 时间升序

    /**
     * @param mDialog   dialog
     * @param dataInter 日期选择回调
     */
    public void builder(final MDialog mDialog, final MDialogInterface.DataInter dataInter, final int orderby) {
        mDialog.initDialog().withTitie("日期")
                .setCustomView(R.layout.dialog_son_datepricker, new MDialog.CustomInter() {
                    @Override
                    public void custom(View customView) {
                        datePicker = (DatePicker) customView.findViewById(R.id.datePicker);
                        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                        DatePrickerUtil.findNumberPicker(datePicker);
                        try {
                            if (orderby == ORDER_BY_DESC) {
                                datePicker.setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse("1898-01-01").getTime());
                                datePicker.setMaxDate(new SimpleDateFormat("yyyy-MM-dd").parse(DatePrickerUtil.getDate()).getTime());
                            } else {
                                datePicker.setMinDate(new SimpleDateFormat("yyyy-MM-dd").parse(DatePrickerUtil.getDate()).getTime());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DatePrickerUtil.setDatePickerDividerColor(datePicker);  // 自定义控件样式
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
                        dataInter.data(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
                        mDialog.dismiss();
                    }
                }).show();
    }


    /**
     * 用于判断月日是否为单数
     *
     * @param data 数据
     * @return 如果为单数前面加0
     */
    public static String isSingle(int data) {
        return (data < 10 ? "0" + data : "" + data);
    }
}
