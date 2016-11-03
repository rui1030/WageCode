package com.dffc.wp.okhttpdownload;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhaokaiqiang on 15/11/22.
 */
public class OkHttpProxy {

//    private static OkHttpClient mHttpClient;

//    private static OkHttpClient init() {
//        synchronized (OkHttpProxy.class) {
//            if (mHttpClient == null) {
//                mHttpClient = NetWorkClient.getOkHttpClient();
//                if (mHttpClient == null) {
//                    mHttpClient = new OkHttpClient();
//                }
//            }
//        }
//        return mHttpClient;
//    }

    public static OkHttpClient getInstance() {
        File cacheDir = new File("/sdcard/wage_cache");
        Cache cache = new Cache(cacheDir, 40 * 1024 * 1024);
        OkHttpClient myClient = new OkHttpClient().newBuilder().cache(cache).build();
        return myClient;
    }

//    public static void setInstance(OkHttpClient okHttpClient) {
//        OkHttpProxy.mHttpClient = okHttpClient;
//    }

    public static Call download(String url, DownloadListener downloadListener) {
        Request request = new Request.Builder().url(url).build();
        Call call = BodyWrapper.addProgressResponseListener(getInstance(), downloadListener).newCall(request);
        call.enqueue(downloadListener);
        return call;
    }

    /**
     * default time out is 30 min
     */
    public static UploadRequestBuilder upload() {
        return new UploadRequestBuilder();
    }

    public static void cancel(Object tag) {
        Dispatcher dispatcher = getInstance().dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


}
