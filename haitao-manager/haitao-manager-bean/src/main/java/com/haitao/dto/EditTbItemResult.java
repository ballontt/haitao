package com.haitao.dto;

/**
 * Created by ballontt on 2017/1/12.
 */
public class EditTbItemResult<T> {
    private boolean success; //结果是否成功
    private T data;          //成功时返回的数据
    private String error;    //失败时返回的错误信息

    public EditTbItemResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public EditTbItemResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "EditTbItemResult{" +
                "success=" + success +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
