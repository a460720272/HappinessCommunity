package com.xww.core.library.net.rest;

import android.content.Context;

import com.xww.core.library.net.loading.av.AVLoaderStyle;
import com.xww.core.library.net.rest.callback.IError;
import com.xww.core.library.net.rest.callback.IFailure;
import com.xww.core.library.net.rest.callback.IRequest;
import com.xww.core.library.net.rest.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能： RestClient 网络框架构建者
 * 使用 android 简化的 builder 设计模式
 *
 * @author : xww
 * @created at : 19-3-12
 * @time : 下午4:09
 */
public class RestClientBuilder {

    private WeakHashMap<String, Object> mParams = RestClientCreator.getParams();
    private String mUrl;
    private Context mContext;
    private Enum<AVLoaderStyle> mStyle;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;
    //文件上传
    private File mFile;
    private String mUploadParamName;
    /**
     * 文件下载
     */
    private String mFileName;
    private String mFileDir;
    private String mExtensionName;

    RestClientBuilder() {
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    public final RestClientBuilder param(String key, Object value) {
        this.mParams.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), raw);
        return this;
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        return this;
    }

    public final RestClientBuilder loader(Context context, Enum<AVLoaderStyle> style) {
        this.mContext = context;
        this.mStyle = style;
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public final RestClientBuilder file(File file) {
        this.mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        this.mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder uploadParam(String uploadParam) {
        this.mUploadParamName = uploadParam;
        return this;
    }

    public final RestClientBuilder fileName(String name) {
        this.mFileName = name;
        return this;
    }

    public final RestClientBuilder fileDir(String dir) {
        this.mFileDir = dir;
        return this;
    }

    public final RestClientBuilder fileExtension(String extension) {
        this.mExtensionName = extension;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, mContext, mStyle, mParams, mRequest, mSuccess, mFailure, mError, mBody,
                mFile, mUploadParamName, mFileName, mFileDir, mExtensionName);
    }
}
