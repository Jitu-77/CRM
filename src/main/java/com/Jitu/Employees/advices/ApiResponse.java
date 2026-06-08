package com.Jitu.Employees.advices;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ApiResponse <T>{

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")  //"08-06-2026 07:56:13"
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse(){
        this.timeStamp = LocalDateTime.now(); // "2026-06-08T19:52:30.8456148" without using @JsonFormat
    }

    public ApiResponse(T data){
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error){
        this();
        this.error = error;
    }
}
