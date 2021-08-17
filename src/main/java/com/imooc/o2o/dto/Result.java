package com.imooc.o2o.dto;

public class Result<T> {
    private boolean success;//是否成功标志

    private T data;//成功时返回的数据

    private String errorMsg;//错误信息

    private int errorCode;

    public Result() {
    }

    //成功时的构造器
    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
