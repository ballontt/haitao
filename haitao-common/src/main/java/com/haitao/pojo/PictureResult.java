package com.haitao.pojo;

/**
 * Created by ballontt on 2017/1/26.
 */
public class PictureResult {
    private int error;
    private String msg;
    private String url;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PictureResult{" +
                "error=" + error +
                ", msg='" + msg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
