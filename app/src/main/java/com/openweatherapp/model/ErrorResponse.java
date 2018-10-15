package com.openweatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vishal
 * Use for handle error
 */
public class ErrorResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("error")
    private Error error;

    public String getMessage() {
        return message;
    }

    public Error getError() {
        return error;
    }
}