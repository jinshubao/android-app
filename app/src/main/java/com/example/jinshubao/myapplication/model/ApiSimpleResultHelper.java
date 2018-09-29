package com.example.jinshubao.myapplication.model;

public class ApiSimpleResultHelper<T> extends ApiResultHelper {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
