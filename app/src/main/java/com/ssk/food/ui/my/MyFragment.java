package com.ssk.food.ui.my;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssk.food.R;
import com.ssk.food.adapter.Apps2Adapter;
import com.ssk.food.adapter.AppsAdapter;
import com.ssk.food.bean.AppsBean;
import com.ssk.food.di.component.DaggerActivityComponent;
import com.ssk.food.di.module.RequestActionModule;
import com.ssk.food.server.RequestAction;
import com.zhtx.mindlib.base.BaseApp;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.di.module.AcitvityModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者:
 * 描述:我的
 */

public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.ContView {

    @BindView(R.id.iv_my_pic)
    ImageView ivMyPic;
    @BindView(R.id.tv_my_name)
    TextView tvMyName;
    @BindView(R.id.tv_my_pofit)
    TextView tvMyPofit;
    @BindView(R.id.btn_my_put_forwary)
    Button btnMyPutForwary;
    @BindView(R.id.rv_my)
    RecyclerView rvMy;

    private List<AppsBean> appsList = new ArrayList<>();
    private Apps2Adapter apps2Adapter;

    @Override
    public int initResource() {
        return R.layout.fragment_my;
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
        apps2Adapter = new Apps2Adapter(appsList);
        rvMy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMy.setAdapter(apps2Adapter);
        initInfo();
    }

    @OnClick(R.id.btn_my_put_forwary)
    public void onViewClicked() {
        putForwary();
    }

    @Override
    public void initInfo() {
        tvMyName.setText("");
        tvMyPofit.setText("");
        String type = mSharedPreferences.getString("", "");
        if (type.equals("")){
            isMain();
        } else {
            isBranch();
        }
    }

    @Override
    public void isBranch() {
        btnMyPutForwary.setVisibility(View.GONE);
        appsList.add(new AppsBean(R.mipmap.ic_my_pwd, getResources().getString(R.string.my_pwd)));
        appsList.add(new AppsBean(R.mipmap.ic_my_setting, getResources().getString(R.string.my_setting)));
        apps2Adapter.notifyDataSetChanged();
    }

    @Override
    public void isMain() {
        btnMyPutForwary.setVisibility(View.VISIBLE);
        appsList.add(new AppsBean(R.mipmap.ic_my_branch, getResources().getString(R.string.my_branch)));
        appsList.add(new AppsBean(R.mipmap.ic_my_alipay, getResources().getString(R.string.my_alipay)));
        appsList.add(new AppsBean(R.mipmap.ic_my_pwd, getResources().getString(R.string.my_pwd)));
        appsList.add(new AppsBean(R.mipmap.ic_my_authentication, getResources().getString(R.string.my_authentication)));
        appsList.add(new AppsBean(R.mipmap.ic_my_setting, getResources().getString(R.string.my_setting)));
        apps2Adapter.notifyDataSetChanged();
    }

    @Override
    public void putForwary() {

    }
}
