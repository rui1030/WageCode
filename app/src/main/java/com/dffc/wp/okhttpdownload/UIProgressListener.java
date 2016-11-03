package com.dffc.wp.okhttpdownload;


/**
 * Created by zhaokaiqiang on 15/11/23.
 */
public interface UIProgressListener {

    void onUIProgress(Progress progress);

    void onUIStart();

    void onUIFinish();
}
