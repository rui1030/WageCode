package com.dffc.wp.net;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by zhuruyi on 16/11/3.
 */
@HttpResponse(parser = ResultParser.class)
public class ResponseEntity {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}