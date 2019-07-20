package com.happinesscommunity.framework.mvp.proxy;


import com.happinesscommunity.framework.mvp.base.IBaseView;

public class ProxyFragment<V extends IBaseView> extends ProxyImpl {
    public ProxyFragment(V view) {
        super(view);
    }
}
