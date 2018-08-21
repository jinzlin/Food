package com.ssk.food.ui.shop;

import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ssk.food.adapter.AppsAdapter;
import com.ssk.food.bean.AppsBean;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.di.module.AcitvityModule;
import com.ssk.food.R;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者:
 * 描述:店铺
 */

public class ShopFragment extends BaseFragment<ShopPresenter> implements ShopContract.ContView {

    @BindView(R.id.rv_shop_01)
    RecyclerView rvShop01;

    @Override
    public int initResource() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initInject() {
        DaggerActivityComponent
                .builder()
                .appComponent(BaseApp.getInstance().getAppComponent())
                .acitvityModule(new AcitvityModule(getActivity()))
                .requestActionModule(new RequestActionModule(new RequestAction()))
                .build()
                .inject(this);
    }

    @Override
    public void initData() {

        List<AppsBean> appsList1 = new ArrayList<>();
        appsList1.add(new AppsBean(R.mipmap.ic_shop_release, getResources().getString(R.string.shop_release)));
        appsList1.add(new AppsBean(R.mipmap.ic_shop_manage_food, getResources().getString(R.string.shop_manage_food)));
        appsList1.add(new AppsBean(R.mipmap.ic_shop_manage_table, getResources().getString(R.string.shop_manage_table)));
        appsList1.add(new AppsBean(R.mipmap.ic_shop_hot_food, getResources().getString(R.string.shop_hot_food)));
        appsList1.add(new AppsBean(R.mipmap.ic_shop_manage_sort, getResources().getString(R.string.shop_manage_sort)));
        appsList1.add(new AppsBean(R.mipmap.ic_shop_restaurant_info, getResources().getString(R.string.shop_restaurant_info)));
        AppsAdapter appsAdapter1 = new AppsAdapter(appsList1);
        rvShop01.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvShop01.setAdapter(appsAdapter1);
    }

    @Override
    public void setShopInfo(String todaySales, String todayOrder, String shopState) {

    }
}
