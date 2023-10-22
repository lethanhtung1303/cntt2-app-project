package com.tdtu.webproject.exception;

import generater.openapi.model.WebApiErrorDetails;
import generater.openapi.model.WebApiErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.StreamSupport;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public Object webApiValidation(ConstraintViolationException ex,
                                   WebRequest request) {
        WebApiErrorResponse resource = new WebApiErrorResponse();
        resource.setStatus(HttpStatus.BAD_REQUEST.value());
        List<WebApiErrorDetails> resultsList = new ArrayList<>();
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        if (set != null) {
            set.forEach(excv -> {
                String name = StreamSupport.stream(excv.getPropertyPath().spliterator(), false)
                        .map(Path.Node::getName).filter(Objects::nonNull).reduce((first, second) -> second)
                        .orElseGet(() -> excv.getPropertyPath().toString());
                String errorCd;
                switch (excv.getConstraintDescriptor().getAnnotation().annotationType().getName()) {
                    case "javax.validation.constraints.Size" -> errorCd = "ERROR_LENGTH_VALIDATION";
                    case "javax.validation.constraints.NotNull" -> errorCd = "ERROR_NOT_NULL_VALIDATION";
                    case "javax.validation.constraints.Min",
                            "javax.validation.constraints.Max",
                            "javax.validation.constraints.DecimalMin",
                            "javax.validation.constraints.DecimalMax" -> errorCd = "ERROR_RANGE_VALIDATION";
                    case "javax.validation.constraints.Pattern" -> errorCd = "ERROR_PATTERN_VALIDATION";
                    case "validator.DateRange" -> {
                        errorCd = "ERROR_DATE_RANGE";
                        name = excv.getConstraintDescriptor().getAttributes().get("from").toString();
                    }
                    default -> errorCd = "ERROR_DATA_TYPE_VALIDATION";
                }
                resultsList.add(getErrorDetail(ex.getMessage(), errorCd, null, name));
            });
        }
        resource.setResults(resultsList);
        return handleExceptionInternal(ex, resource, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object handleInternalServerError(RuntimeException ex, WebRequest request) {
        WebApiErrorResponse resource = new WebApiErrorResponse();
        resource.setStatus(HttpStatus. INTERNAL_SERVER_ERROR.value());
        resource.setResults(List.of(getErrorDetail(ex.getMessage(), "ERROR_OTHER", null, null)));
        return handleExceptionInternal(ex, resource, new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
    @ExceptionHandler({BusinessException.class})
    public Object handleBusinessException(BusinessException ex,WebRequest request) {
        WebApiErrorResponse resource = new WebApiErrorResponse();
        resource.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resource.setResults(List.of(getErrorDetail(ex.getMessage(), "ERROR_OTHER", null, null)));
        return handleExceptionInternal(ex, resource, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
        private WebApiErrorDetails getErrorDetail(String message,
                                                  String errorCd,
                                                  List<Integer> errorIdList,
                                                  String field) {
            WebApiErrorDetails detailError = new WebApiErrorDetails();
            detailError.setMessage(message);
            detailError.setErrorCd(errorCd);
            detailError.setErrorIdList(errorIdList);
            detailError.setField(field);
            return detailError;
        }
}