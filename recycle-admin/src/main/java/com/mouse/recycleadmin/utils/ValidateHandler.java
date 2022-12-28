package com.mouse.recycleadmin.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ValidateHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            // 将异常字段的信息提取
            stringBuilder.append("  ").append(defaultMessage);
            break;
        }
        // 这段注释是对返回值进行了统一处理，为了简化，这段代码注释了，直接将异常信息封装到ResponseEntity返回
//         return new ResponseEntity(ResultWrapper.builder().code(301).msg(stringBuilder.toString()).build(),HttpStatus.OK);
        return new ResponseEntity(Result.error(stringBuilder.toString()),HttpStatus.OK);
//        return new ResponseEntity(stringBuilder.toString(), HttpStatus.OK);

    }
}