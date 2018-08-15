package com.ssk.food.utils.dialog;


/**
 * 作者: Coding Farmer_5199.
 * @date 2017/11/6
 * MDialog接口类
 */

public interface MDialogInterface {

    interface DataInter {
        /**
         * 日期选择回调
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void data(int year, int month, int day);
    }

    interface ListViewOnClickInter {
        /**
         * listview单选选择回调
         *
         * @param data     选择的数据
         * @param position 选择的位置
         */
        void onItemClick(String data, int position);
    }

    interface PasswordInter {
        /**
         * 输入密码回调
         *
         * @param data 密码
         */
        void callback(String data);
    }


    interface Linkage1Inter {
        /**
         * 1级联动选择回调
         *
         * @param data1  数据1
         * @param position1  位置1
         */
        void data(String data1, int position1);
    }

    interface Linkage2Inter {
        /**
         * 2级联动选择回调
         *
         * @param data1  数据1
         * @param data2  数据2
         * @param position1  位置1
         * @param position1  位置2
         */
        void data(String data1, String data2, int position1, int position2);
    }
}
