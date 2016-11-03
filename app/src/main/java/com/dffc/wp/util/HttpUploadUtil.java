package com.dffc.wp.util;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhuruyi on 16/11/3.
 */
public class HttpUploadUtil {
    private static final String TAG = "HttpUploadUtil";

    public void upload() {
        Log.d(TAG, "upload begin");
        HttpURLConnection connection = null;
        DataOutputStream dos = null;
        FileInputStream fin = null;

        String boundary = "---------------------------265001916915724";
        // 真机调试的话，这里url需要改成电脑ip
        // 模拟机用10.0.0.2,127.0.0.1被tomcat占用了
        String urlServer = "http://10.0.0.2:8080/TestWeb/command=UpdatePicture";
        String lineEnd = "\r\n";
        String pathOfPicture = "/sdcard/aaa.jpg";
        int bytesAvailable, bufferSize, bytesRead;
        int maxBufferSize = 1 * 1024 * 512;
        byte[] buffer = null;

        try {
            Log.d(TAG, "try");
            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            // 允许向url流中读写数据
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(true);

            // 启动post方法
            connection.setRequestMethod("POST");

            // 设置请求头内容
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "text/plain");

            // 伪造请求头
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);


            // 开始伪造POST Data里面的数据
            dos = new DataOutputStream(connection.getOutputStream());
            fin = new FileInputStream(pathOfPicture);

            Log.d(TAG, "开始上传images");
            //--------------------开始伪造上传images.jpg的信息-----------------------------------
            String fileMeta = "--" + boundary + lineEnd +
                    "Content-Disposition: form-data; name=\"uploadedPicture\"; filename=\"" + pathOfPicture + "\"" + lineEnd +
                    "Content-Type: image/jpeg" + lineEnd + lineEnd;
            // 向流中写入fileMeta
            dos.write(fileMeta.getBytes());

            // 取得本地图片的字节流，向url流中写入图片字节流
            bytesAvailable = fin.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fin.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fin.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fin.read(buffer, 0, bufferSize);
            }
            dos.writeBytes(lineEnd + lineEnd);
            //--------------------伪造images.jpg信息结束-----------------------------------
            Log.d(TAG, "结束上传");

            // POST Data结束
            dos.writeBytes("--" + boundary + "--");

            // Server端返回的信息
            System.out.println("" + connection.getResponseCode());
            System.out.println("" + connection.getResponseMessage());

            if (dos != null) {
                dos.flush();
                dos.close();
            }
            Log.d(TAG, "upload success-----------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
