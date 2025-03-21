package me.gruzdeva.imageLoader.controller;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"status", "result"})
public class ApiResponseDto {

    public static final String STATUS = "status";
    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";

    private String status;
    private List<Object> result;

    public ApiResponseDto(String status, Object result) {
        this.status = status;
        this.result = new ArrayList<>();
        this.result.add(result);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }
}