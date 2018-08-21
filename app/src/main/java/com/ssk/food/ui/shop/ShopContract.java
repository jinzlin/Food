package com.ssk.food.ui.shop;

import com.zhtx.mindlib.base.BaseContract;

/**
 * 作者:
 * 描述:店铺
 */

public interface ShopContract {

    interface ContView extends BaseContract.BaseView {

        /**
         * 初始化店铺数据
         * @param todaySales    今日销售额
         * @param todayOrder    今日成交订单
         * @param shopState     店鋪状态
         */
        void setShopInfo(String todaySales, String todayOrder, String shopState);
    }

    interface ContPresenter<T> extends BaseContract.BasePresenter<T> {
        void requestShopData();
    }
}
