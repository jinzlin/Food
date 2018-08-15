package com.zhtx.mindlib.base.refreshload;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhtx.mindlib.R;
import com.zhtx.mindlib.base.BaseActivity;
import com.zhtx.mindlib.base.bean.BaseRLBean;
import com.zhtx.mindlib.utils.L;
import com.zhtx.mindlib.utils.MRefreshLoad;

import java.util.ArrayList;
import java.util.List;

import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.complete;
import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.end;
import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.fail;
import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.goneend;

/**
 * 作者: ljz.
 * 描述：刷新加载BaseRLActivity
 */

public abstract class BaseRLActivity<A extends BaseQuickAdapter, B extends BaseRLBean, RL extends BaseRLContract.BaseRLPresenter> extends BaseActivity<RL> implements BaseRLContract.BaseRLView<B>, MRefreshLoad.RefreshLoadListener{

    public MRefreshLoad<A> mRefreshLoad;        // 刷新加载类
    public A refreshLoadAdapter;                // 适配器
    public List<B> list = new ArrayList<>();    // 列表数据
    public boolean isRefresh = true;            // 是否是刷新
    public int page;                            // 页数

    @Override
    public void refreshLoadComplete(List<B> list, int page) {
        // 用于确定加载更多的起始位置
        this.list.addAll(list);
        this.page = page;
        int listSise = list.size();
        int maxItem = mRefreshLoad.getItemNum();
        int preEndIndex = this.list.size() + 1;
        if (page == 1) {
            refreshLoadAdapter.notifyDataSetChanged();
            if (this.list.size() == 0) {
                refreshLoadAdapter.setEmptyView(mRefreshLoad.getEmptyView(this));
            }
        } else if(page == -1) {
            refreshLoadAdapter.notifyDataSetChanged();
        }else {
            refreshLoadAdapter.notifyItemRangeInserted(preEndIndex, listSise);
        }
        if (isRefresh) {
            // 刷新完成
            mRefreshLoad.setRefreshComplete();
        } else {
            if (maxItem != -1 && listSise == maxItem) {
                // 本次加载完成
                mRefreshLoad.setLoadMore(complete);
            }
        }
        if (maxItem != -1 && listSise != maxItem) {
            // 没有更多
            mRefreshLoad.setLoadMore(end);
        }
        if (this.page == -1){
            mRefreshLoad.setLoadMore(goneend);
        }
    }


    @Override
    public boolean isRefresh() {
        return isRefresh;
    }

    @Override
    public void refreshError() {
        mRefreshLoad.setRefreshComplete();
    }

    @Override
    public void loadError() {
        mRefreshLoad.setLoadMore(fail);
    }

    @Override
    public void onRefresh() {
        list.clear();
        isRefresh = true;
        mPresenter.requestList(0);
    }

    @Override
    public void onLoading() {
        isRefresh = false;
        mPresenter.requestList(page);
    }
}
