package com.salesianostriana.dam.finalapi.errores;

import com.salesianostriana.dam.finalapi.errores.excepciones.AppointmentNotAvailableException;
import com.salesianostriana.dam.finalapi.errores.excepciones.BadRequestException;
import com.salesianostriana.dam.finalapi.errores.excepciones.CantCommentWithoutAppointmentException;
import com.salesianostriana.dam.finalapi.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.dam.finalapi.errores.modelo.ApiError;
import com.salesianostriana.dam.finalapi.errores.modelo.ApiSubError;
import com.salesianostriana.dam.finalapi.errores.modelo.ApiValidationSubError;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError404(ex, request);
    }

    @ExceptionHandler({CantCommentWithoutAppointmentException.class})
    public ResponseEntity<?> handleCantCommentWithoutAppointmentException(CantCommentWithoutAppointmentException ex, WebRequest request) {
        return buildApiError400(ex, request);
    }

    //Exception handler for AppointmentNotAvailableException
    @ExceptionHandler({AppointmentNotAvailableException.class})
    public ResponseEntity<?> handleAppointmentNotAvailableException(AppointmentNotAvailableException ex, WebRequest request) {
        return buildApiError400(ex, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST,
                "Errores varios en la validaci??n",
                request,
                ex.getConstraintViolations()
                        .stream()
                        .map(cv -> ApiValidationSubError.builder()
                                .objeto(cv.getRootBeanClass().getSimpleName())
                                .campo(((PathImpl)cv.getPropertyPath()).getLeafNode().asString())
                                .valorRechazado(cv.getInvalidValue())
                                .mensaje(cv.getMessage())
                                .build())
                        .collect(Collectors.toList())
                );
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
        return buildApiError400(ex, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            // Si el error de validaci??n se ha producido a ra??z de una anotaci??n en un atributo...
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(fieldError.getObjectName())
                                .campo(fieldError.getField())
                                .valorRechazado(fieldError.getRejectedValue())
                                .mensaje(fieldError.getDefaultMessage())
                                .build()
                );
            }
            else // Si no, es que se ha producido en una anotaci??n a nivel de clase
            {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(objectError.getObjectName())
                                .mensaje(objectError.getDefaultMessage())
                                .build()
                );
            }
        });
        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores varios en la validaci??n",
                request,
                /*ex.getFieldErrors()
                        .stream().map(error -> ApiValidationSubError.builder()
                                .objeto(error.getObjectName())
                                .campo(error.getField())
                                .valorRechazado(error.getRejectedValue())
                                .mensaje(error.getDefaultMessage())
                                .build())
                        .collect(Collectors.toList())
                */
                subErrorList.isEmpty() ? null : subErrorList
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiErrorStatus(status, ex, request);
    }

    private ResponseEntity<Object> buildApiError400(Exception ex, WebRequest request) {
        return buildApiErrorStatus(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ResponseEntity<Object> buildApiError404(Exception ex, WebRequest request) {
        return buildApiErrorStatus(HttpStatus.NOT_FOUND, ex, request);
    }

    private ResponseEntity<Object> buildApiErrorStatus(HttpStatus status, Exception ex, WebRequest request) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(status, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));
    }

    private ResponseEntity<Object> buildApiErrorWithSubError(HttpStatus estado, String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(estado)
                .body(new ApiError(estado, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }
}
