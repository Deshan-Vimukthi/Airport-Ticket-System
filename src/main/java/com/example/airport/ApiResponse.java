package com.example.airport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    // Getters and Setters
    private boolean success;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private LocalDateTime timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    public ApiResponse(boolean success,String message,T data,String errorCode){
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
    }

    public static <T> ApiResponse<T> success(String message,T data){
        return new ApiResponse<>(true,message,data,null);
    }

    public static <T> ApiResponse<T> success(T data){
        return new ApiResponse<>(true,"",data,null);
    }

    public static <T> ApiResponse<T> success(String message){
        return new ApiResponse<>(true,message,null,null);
    }

    public static <T> ApiResponse<T> error(String message,String errorCode){
        return new ApiResponse<>(false,message,null,errorCode);
    }

    public static <T> ApiResponse<T> error(String message){
        return new ApiResponse<>(false,message,null,null);
    }


}
