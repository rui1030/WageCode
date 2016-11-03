package com.dffc.wp;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.dffc.wp.net.BaseResponse;
import com.dffc.wp.net.ResponseEntity;
import com.dffc.wp.okhttpdownload.OkHttpDownUpUtil;
import com.dffc.wp.okhttpdownload.Progress;
import com.dffc.wp.okhttpdownload.UploadListener;
import com.dffc.wp.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by zhuruyi on 16/11/1.
 */
public class JavaScriptObject {
    private static final String TAG = "JavaScriptObject";

    String uploadUrl = "http://192.168.70.107:8089/tv_payment/V1.0.0/uploadTransferInfo.do?";

    Context mContxt;
    WebView webView;

    String pathUpload = "";

    public JavaScriptObject(Context mContxt, WebView webView) {
        this.mContxt = mContxt;
        this.webView = webView;
    }

    @JavascriptInterface //sdk17版本以上加上注解
    public String chooseExcel() {
        JSONObject result = new JSONObject();

        try{
            String dirPath = "/storage/udisk/kingston/excel";
//            String dirPath = "/sdcard/excel";
            File dir = new File(dirPath);
            if(!dir.exists()){
                result.put("status", false);
                result.put("error", dirPath + " doesn't exits");
            }else {
                result.put("status", true);
                result.put("error", "");

                String[] fileArr = dir.list();
                JSONArray jsonArray = new JSONArray();

                for (String filename : fileArr) {
                    JSONObject object = new JSONObject();
                    object.put("filename", filename);
                    object.put("filepath", dir.getAbsolutePath() + "/" + filename);
                    jsonArray.put(object);
                }

                result.put("result", jsonArray);
            }
        }catch (Exception e){
            try {
                result.put("status", false);
                result.put("error", e.getMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        Log.d(TAG, "chooseExcel=>" + result.toString());
        return result.toString();
    }

    @JavascriptInterface //sdk17版本以上加上注解
    public void uploadExcel(final String filepath) {
        Log.d(TAG, "will uploadExcel filePath=>" + filepath);
        pathUpload = filepath;
        if(TextUtils.isEmpty(filepath)){
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:uploadResult('" + filepath + "','false', 'path is null')");
                }
            });
        }
        upload();
    }

    public void upload(){
        RequestParams params = new RequestParams(uploadUrl);
        params.addBodyParameter("file", new File(pathUpload));
        params.addBodyParameter("transferType", "10");
        params.addBodyParameter("fileFileName", "1.xls");

        Callback.Cancelable cancelable
                = x.http().post(params, new Callback.CommonCallback<ResponseEntity>(){
            @Override
            public void onSuccess(ResponseEntity result) {
                Log.d(TAG, "result.getResult() =>" + result.getResult().toString());
                final BaseResponse response = JsonUtils.toObject(result.getResult().toString(), BaseResponse.class);
                Log.d(TAG, "response=>" + response.toString());
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:uploadResult('" + pathUpload + "','" + response.isSuccess() + "','"+ response.getResult() +"')");
                    }
                });
            }

            @Override
            public void onError(final Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                webView.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl("javascript:uploadResult('" + pathUpload + "','false','"+ ex.getMessage() +"')");
                    }
                });
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

//    private void okhttpUpload(){
        //        Map<String, String > params = new HashMap<>();
//        params.put("transferType", "10");
//        params.put("fileFileName", "1.xls");
//
//
//        OkHttpDownUpUtil.uploadFile(params, new File(demoPath), uploadUrl, new UploadListener() {
//            @Override
//            public void onSuccess(Response response) {
//                Log.d(TAG, "upload success");
////                //TODO upload work
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadUrl("javascript:uploadResult('" + filepath + "','true')");
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                Log.d(TAG, "upload error", e);
////                Log.d(TAG, "upload fail");
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.loadUrl("javascript:uploadResult('" + filepath + "','fail')");
//                    }
//                });
//            }
//
//            @Override
//            public void onUIProgress(Progress progress) {
//                int rate = (int) (progress.getCurrentBytes() * 100.0f / progress.getTotalBytes());
//                Log.d(TAG, "upload rate=>" + rate);
//            }
//        });
//    }
}
