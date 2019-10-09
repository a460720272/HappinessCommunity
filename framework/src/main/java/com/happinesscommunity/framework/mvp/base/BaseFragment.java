package com.happinesscommunity.framework.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.happinesscommunity.framework.mvp.proxy.ProxyFragment;
import com.xww.core.library.util.log.LoggerUtil;

/**
 * 基类 Fragment ，提供懒加载功能
 */
public abstract class BaseFragment extends Fragment implements IBaseView {

    // 是否初始化了 View
    private boolean isActivityCreated = false;
    // fragment 是否可见
    private boolean isVisibleToUser = false;
    // 数据是否已经加载了
    private boolean isDataLoaded = false;

    protected Context mContext;

    //代理 Fragment
    private ProxyFragment mProxyFragment;

    protected abstract @LayoutRes
    int setLayout();

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    protected abstract void initData();

    // 懒加载方法
    protected abstract void loadLazyData();

    @SuppressWarnings("ConstantConditions")
    protected <T extends View> T $(@IdRes int viewId) {
        return this.getView().findViewById(viewId);
    }

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), container, false);

        mProxyFragment = createProxyFragment();
        mProxyFragment.bindPresenter();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        mContext = getContext();

        initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            loadLazyOnce();
        }
    }

    // 懒加载：数据加载仅一次
    private void loadLazyOnce() {
        if (isActivityCreated && isVisibleToUser && !isDataLoaded) {
            loadLazyData();
            isDataLoaded = true;
        }
    }

    @SuppressWarnings("unchecked")
    private ProxyFragment createProxyFragment() {
        if (mProxyFragment == null) {
            return new ProxyFragment<>(this);
        }
        return mProxyFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxyFragment.unbindPresenter();
    }
}
