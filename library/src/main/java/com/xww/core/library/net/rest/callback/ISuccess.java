package com.xww.core.library.net.rest.callback;

/**
 * 功能：请求成功时回调
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午2:41
 */
public interface ISuccess {
    /**
     * @param response 返回请求成功时的 response
     */
    void onSuccess(String response);
}
