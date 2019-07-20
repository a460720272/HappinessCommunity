package com.xww.core.library.net.rest.download;

import android.os.AsyncTask;

import com.xww.core.library.net.rest.RestClientCreator;
import com.xww.core.library.net.rest.callback.IError;
import com.xww.core.library.net.rest.callback.IFailure;
import com.xww.core.library.net.rest.callback.IRequest;
import com.xww.core.library.net.rest.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 功能：下载文件请求，在请求时开始下载任务
 *
 * @author : xww
 * @created at : 19-3-15
 * @time : 下午8:20
 */
public class DownloadTask {
    //存放请求参数的容器
    private final WeakHashMap<String, Object> PARAMS;
    //请求的 url
    private final String URL;
    //请求开启与结束回调
    private final IRequest REQUEST;
    //请求成功回调
    private final ISuccess SUCCESS;
    //请求失败回调
    private final IFailure FAILURE;
    //请求错误回调
    private final IError ERROR;
    //文件完整名字
    private final String FILE_NAME;
    //文件路径
    private final String FILE_DIR;
    //文件扩展名
    private final String EXTENSION_NAME;

    public DownloadTask(WeakHashMap<String, Object> params, String url, IRequest request, ISuccess success,
                        IFailure failure, IError error, String fileName, String fileDir, String extensionName) {
        this.PARAMS = params;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.FILE_NAME = fileName;
        this.FILE_DIR = fileDir;
        this.EXTENSION_NAME = extensionName;
    }

    public final void start() {
        startRequest();

        RestClientCreator.getRestService()
                .download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (call.isExecuted()) {
                            if (response.isSuccessful()) {

                                if (SUCCESS != null) {
                                    //得到响应的内容
                                    final ResponseBody responseBody = response.body();
                                    /*
                                     * 开启保存文件任务
                                     */
                                    final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                            FILE_DIR, EXTENSION_NAME, responseBody, FILE_NAME);

                                    //这里一定要注意判断，否则文件下载不全
                                    if (task.isCancelled()) {
                                        stopRequest();
                                    }
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }

                        requestFinished();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }

                        stopRequest();
                        requestFinished();
                    }
                });
    }

    /**
     * 请求完成时调用
     */
    private void requestFinished() {
        //清空参数容器缓存，释放资源
        RestClientCreator.getParams().clear();
    }

    /**
     * 请求开始
     */
    private void startRequest() {
        if (REQUEST != null) {
            //下载开始时回调
            REQUEST.requestStart();
        }
    }

    /**
     * 请求结束
     */
    private void stopRequest() {
        if (REQUEST != null) {
            //下载结束时回调
            REQUEST.requestStop();
        }
    }
}
