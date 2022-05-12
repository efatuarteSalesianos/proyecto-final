package com.salesianostriana.dam.finalapi.errors.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private int code;
    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status, String message, String path) {
        this.status = status;
        this.code = status.value();
        this.message = message;
        this.date = LocalDateTime.now();
        this.path = path;
    }

    public ApiError(HttpStatus status, String message, String path, List<ApiSubError> subErrors) {
        this(status, message, path);
        this.subErrors = subErrors;
    }


}