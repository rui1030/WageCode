package com.dffc.wp.okhttpdownload;

import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.util.Map;

/**
 * Created by zhuruyi on 16/4/21.
 */
public class OkHttpDownUpUtil {
    private static final String TAG = "OkHttpDownUpUtil";

    /**
     * 采用七牛上传接口，Token有效期为12小时，若Token无效，请在下面自行获取
     * Token生成网址 http://jsfiddle.net/gh/get/extjs/4.2/icattlecoder/jsfiddle/tree/master/uptoken
     * <p/>
     * AK = IUy4JnOZHP6o-rx9QsGLf9jMTAKfRkL07gNssIDA
     * CK = DkfA7gPTNy1k4HWnQynra3qAZhrzp-wmSs15vub6
     * BUCKE_NAME = zhaokaiqiang
     */
    public static void uploadFile(Map<String, String> param, File sourceFile, String URL_UPLOAD, UploadListener uploadListener) {

        if (!sourceFile.exists()) {
            Log.e(TAG, sourceFile + " doesn't exits");
            return;
        }

        Pair<String, File> pair = new Pair("file", sourceFile);

        OkHttpProxy
                .upload()
                .url(URL_UPLOAD)
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(uploadListener);
//                new UploadListener() {
//                    @Override
//                    public void onSuccess(Response response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//
//                    }
//
//                    @Override
//                    public void onUIProgress(Progress progress) {
//                        int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
//                        if (pro > 0) {
//                            ((ProgressBar)progressBar).setProgress(pro);
//                        }
//                    }
//                };
    }

    public static void downloadFile(String URL_DOWMLOAD, DownloadListener downloadListener) {

        OkHttpProxy.download(URL_DOWMLOAD, downloadListener);

//        new DownloadListener(desFileDir, "json.jar") {
//
//            @Override
//            public void onUIProgress(Progress progress) {
//                //当下载资源长度不可知时，progress.getTotalBytes()为-1，此时不能显示下载进度
//                int pro = (int) (progress.getCurrentBytes() / progress.getTotalBytes() * 100);
//                if (pro > 0) {
//                    progressBar.setProgress(pro);
//                }
//            }
//
//            @Override
//            public void onSuccess(File file) {
//
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//
//            }
//        };
    }
}
