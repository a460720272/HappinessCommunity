package com.xww.core.library.net.rest.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.xww.core.library.app.AppConfiguration;
import com.xww.core.library.net.rest.callback.IRequest;
import com.xww.core.library.net.rest.callback.ISuccess;
import com.xww.core.library.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * 功能：在下载文件中进行保存文件的任务
 *
 * @author : xww
 * @created at : 19-3-16
 * @time : 下午5:58
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    SaveFileTask(IRequest request, ISuccess success) {
        this.SUCCESS = success;
        this.REQUEST = request;
    }

    @Override
    protected File doInBackground(Object... objects) {
        //文件夹路径
        String fileDir = (String) objects[0];
        //扩展名
        String extension = (String) objects[1];
        //响应内容
        final ResponseBody body = (ResponseBody) objects[2];
        //文件名
        final String fileName = (String) objects[3];
        final InputStream is = body.byteStream();

        if (fileDir == null || fileDir.equals("")) {
            fileDir = "down_loads";
        }

        if (extension == null || extension.equals("")) {
            extension = "";
        }

        if (fileName == null) {
            return FileUtil.writeToDisk(is, fileDir, extension.toUpperCase(), extension);
        } else {
            return FileUtil.writeToDisk(is, fileDir, fileName);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.requestStop();
        }

        autoInstallApk(file);
    }

    /**
     * 进行自动安装程序
     */
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            AppConfiguration.getInstance().getAppContext().startActivity(install);
        }
    }
}
