package com.xww.core.library.net.rest;

import android.content.Context;

import com.xww.core.library.app.AppConfiguration;
import com.xww.core.library.net.loading.av.AVLoadingDialog;
import com.xww.core.library.net.rest.callback.IError;
import com.xww.core.library.net.rest.callback.IFailure;
import com.xww.core.library.net.rest.callback.IRequest;
import com.xww.core.library.net.rest.callback.ISuccess;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功能： RestClient 请求回调
 * IRequest : 请求开始、结束回调
 * ISuccess : 请求成功回调，返回 string
 * IFailure : 请求失败回调
 * IError : 请求错误回调，返回 code 和 message
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午7:29
 */
@SuppressWarnings("NullableProblems")
public class RestCallback implements Callback<String> {

    private final Context CONTEXT;
    //请求开启与结束回调
    private final IRequest REQUEST;
    //请求成功回调
    private final ISuccess SUCCESS;
    //请求失败回调
    private final IFailure FAILURE;
    //请求错误回调
    private final IError ERROR;

    public RestCallback(Context context, IRequest request, ISuccess success, IFailure failure, IError error) {
        this.CONTEXT = context;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {

                if (SUCCESS != null) {
                    //请求成功，回调 string 类型
                    SUCCESS.onSuccess(response.body());
                }

                stopRequest();
            }
        } else {

            if (ERROR != null) {
                //请求错误，返回错误码和错误信息
                ERROR.onError(response.code(), response.message());
            }
        }

        requestFinished();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (call.isExecuted()) {
            if (FAILURE != null) {
                //请求失败回调
                FAILURE.onFailure();
            }

            stopRequest();
        }

        requestFinished();
    }

    /**
     * 请求完成后执行
     */
    private void requestFinished() {
        RestClientCreator.getParams().clear();
    }

    /**
     * 请求结束
     */
    private void stopRequest() {
        if (CONTEXT != null) {
            AppConfiguration.getInstance().getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AVLoadingDialog.stopLoading();
                }
            }, 1000);
        }

        if (REQUEST != null) {
            //下载结束时回调
            REQUEST.requestStop();
        }
    }
}
