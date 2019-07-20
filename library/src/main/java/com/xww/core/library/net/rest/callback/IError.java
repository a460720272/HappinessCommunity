package com.xww.core.library.net.rest.callback;

/**
 * 功能：请求错误时回调
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午2:39
 */
public interface IError {
    /**\
     * @param code 错误码
     * @param msg　错误提示信息
     */
    void onError(int code, String msg);
}
