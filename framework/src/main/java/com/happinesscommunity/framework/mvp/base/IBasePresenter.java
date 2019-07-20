package com.happinesscommunity.framework.mvp.base;

public interface IBasePresenter {

    void attach(IBaseView view);

    void detach();
}
