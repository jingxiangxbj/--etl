package com.jingxiang.datachange.exception;

import java.lang.RuntimeException;

public class StorageException extends RuntimeException {
 
    public StorageException(String message) {
        super(message);
    }
 
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

