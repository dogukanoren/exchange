package com.dogukanoren.exchange.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ApiError {

    @Id
    @GeneratedValue
    private Long id;

    private int status;
    private String message;
    private Long timeStamp;
    private String path;

    public ApiError() {

    }

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date().getTime();
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
