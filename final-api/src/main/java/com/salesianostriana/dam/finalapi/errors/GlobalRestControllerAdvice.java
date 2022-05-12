package com.salesianostriana.dam.finalapi.errors;

import com.salesianostriana.dam.finalapi.errors.exceptions.*;
import com.salesianostriana.dam.finalapi.errors.models.ApiError;
import com.salesianostriana.dam.finalapi.errors.models.ApiSubError;
import com.salesianostriana.dam.finalapi.errors.models.ApiValidationSubError;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(fieldError.getObjectName())
                                .field(fieldError.getField())
                                .rejectedValue(fieldError.getRejectedValue())
                                .message(fieldError.getDefaultMessage())
                                .build()
                );
            } else {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .object(objectError.getObjectName())
                                .message(objectError.getDefaultMessage())
                                .build()
                );
            }

        });

        return buildApiError("Form contains errors", request, subErrorList);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({FileNotSupportedException.class})
    public ResponseEntity<?> handleFileNotSupportedException(FileNotSupportedException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({ListEntityNotFoundException.class})
    public ResponseEntity<?> handleListEntityNotFoundException(ListEntityNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({PasswordMissMatchException.class})
    public ResponseEntity<?> handlePasswordMissMatchException(PasswordMissMatchException ex, WebRequest request) {
        return buildApiErrorBadRequest(ex, request);
    }

    @ExceptionHandler({SingleEntityNotFoundException.class})
    public ResponseEntity<?> handleSingleEntityNotFoundException(SingleEntityNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({StorageException.class})
    public ResponseEntity<?> handleStorageException(StorageException ex, WebRequest request) {
        return buildApiError(ex, request);
    }

    @ExceptionHandler({UnauthorizeException.class})
    public ResponseEntity<?> handleUnauthorizeException(UnauthorizeException ex, WebRequest request) {
        return buildApiErrorUnahutorized(ex, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleAccesDeniedException(AccessDeniedException ex, WebRequest request){
        return buildApiErrorBadRequest(ex,request);
    }

    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<?> handleMultipartException(MultipartException ex, WebRequest request){
        return buildApiError(ex,request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex,WebRequest request){
        return buildApiErrorBadRequest(ex,request);
    }

    @ExceptionHandler({EntityExistsException.class})
    public ResponseEntity<?> handleEntityExistsException(EntityExistsException ex,WebRequest request){
        return buildApiErrorBadRequest(ex,request);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstrintViolationException(ConstraintViolationException ex, WebRequest request) {
        return buildApiError("Form contains multiple errors",
                request,
                ex.getConstraintViolations()
                        .stream()
                        .map(cv -> ApiValidationSubError.builder()
                                .object(cv.getRootBeanClass().getSimpleName())
                                .field(((PathImpl) cv.getPropertyPath()).getLeafNode().asString())
                                .rejectedValue(cv.getInvalidValue())
                                .message(cv.getMessage())
                                .build())
                        .collect(Collectors.toList())
        );

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request);
    }

    private final ResponseEntity<Object> buildApiError(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }

    private final ResponseEntity<Object> buildApiErrorBadRequest(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }
    private final ResponseEntity<Object> buildApiErrorUnahutorized(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }

    private ResponseEntity<Object> buildApiError(String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }

}