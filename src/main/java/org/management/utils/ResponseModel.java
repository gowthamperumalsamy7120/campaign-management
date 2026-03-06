package org.management.utils;

import lombok.Data;

@Data
public class ResponseModel<T> {
    private String message;
    private boolean success;
    private T data;

    public ResponseModel(boolean success, String message, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public ResponseModel(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }
}
