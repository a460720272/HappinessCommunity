package com.happinesscommunity.framework.mvp.proxy;


import com.happinesscommunity.framework.mvp.base.IBaseView;

public class ProxyActivity<V extends IBaseView> extends ProxyImpl {
    public ProxyActivity(V view) {
        super(view);
    }
}
