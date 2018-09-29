package com.example.jinshubao.myapplication.model;

import java.util.List;

public class ApiResultListHelper<T> extends ApiResultHelper {

    private List<T> resultList;

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
