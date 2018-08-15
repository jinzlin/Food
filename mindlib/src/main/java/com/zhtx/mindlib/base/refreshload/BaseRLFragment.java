package com.zhtx.mindlib.base.refreshload;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhtx.mindlib.base.BaseFragment;
import com.zhtx.mindlib.base.bean.BaseRLBean;
import com.zhtx.mindlib.utils.MRefreshLoad;

import java.util.ArrayList;
import java.util.List;

import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.complete;
import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.end;
import static com.zhtx.mindlib.utils.MRefreshLoad.LoadType.fail;

/**
 * 作者: ljz.
 * 描述：刷新加载BaseRLFragment
 */

public abstract class BaseRLFragment<A extends BaseQuickAdapter, B extends BaseRLBean, RL extends BaseRLContract.BaseRLPresenter> extends BaseFragment<RL> implements BaseRLContract.BaseRLView<B>, MRefreshLoad.RefreshLoadListener{

    public MRefreshLoad<A> mRefreshLoad;
    public A refreshLoadAdapter;
    public List<B> list = new ArrayList<>();

    // 是否是刷新
    public boolean isRefresh = true;
    public int page;

    @Override
    public void refreshLoadComplete(List<B> list, int page) {
        // 用于确定加载更多的起始位置
        this.list.addAll(list);
        this.page = page;
        int preEndIndex = this.list.size() + 1;
        if (page == 1) {
            this.list.clear();
            this.list.addAll(list);
            refreshLoadAdapter.notifyDataSetChanged();
            if (this.list.size() == 0) {
                refreshLoadAdapter.setEmptyView(mRefreshLoad.getEmptyView(getActivity()));
            }
        } else {
            refreshLoadAdapter.notifyItemRangeInserted(preEndIndex, list.size());
        }
        if (isRefresh) {
            // 刷新完成
            mRefreshLoad.setRefreshComplete();
        } else {
            if (list.size() == mRefreshLoad.getItemNum()) {
                // 本次加载完成
                mRefreshLoad.setLoadMore(complete);
            }
        }
        if (list.size() != mRefreshLoad.getItemNum()) {
            // 没有更多
            mRefreshLoad.setLoadMore(end);
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
