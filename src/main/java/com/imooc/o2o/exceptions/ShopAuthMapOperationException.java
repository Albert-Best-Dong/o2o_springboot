package com.imooc.o2o.exceptions;

import java.io.Serializable;

public class ShopAuthMapOperationException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 4312244951695148112L;


	public ShopAuthMapOperationException(String msg) {
		super(msg);
	}
}
