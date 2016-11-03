package com.dffc.wp.okhttpdownload;

public class UIHandler extends ProgressHandler {

    public UIHandler(UIProgressListener uiProgressListener) {
        super(uiProgressListener);
    }

    @Override
    public void start(UIProgressListener listener) {
        listener.onUIStart();
    }

    @Override
    public void progress(UIProgressListener uiProgressListener, Progress progress) {
        uiProgressListener.onUIProgress(progress);
    }

    @Override
    public void finish(UIProgressListener listener) {
        listener.onUIFinish();
    }
}
