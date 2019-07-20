package com.xww.core.library.net.rest.callback;

/**
 * 功能：
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午5:50
 */
public interface IRequest {
    /**
     * 请求开始时回调
     */
    void requestStart();

    /**
     * 请求结束时回调
     */
    void requestStop();
}
