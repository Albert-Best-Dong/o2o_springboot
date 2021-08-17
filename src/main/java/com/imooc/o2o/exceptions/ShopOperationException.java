package com.imooc.o2o.exceptions;

public class ShopOperationException extends RuntimeException{

    //序列化
    private static final long serialVersionUID = 2361446884822298905L;

    public ShopOperationException(String msg) {
        super(msg);
    }
}
