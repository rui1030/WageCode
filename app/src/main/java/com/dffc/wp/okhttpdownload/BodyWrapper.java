package com.dffc.wp.okhttpdownload;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BodyWrapper {

    public static OkHttpClient addProgressResponseListener(OkHttpClient client, final ProgressListener progressListener) {
        return client.newBuilder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ResponseProgressBody(originalResponse.body(), progressListener))
                                .build();
                    }
                }).build();
    }

    public static RequestProgressBody addProgressRequestListener(RequestBody requestBody, ProgressListener progressRequestListener) {
        return new RequestProgressBody(requestBody, progressRequestListener);
    }
}