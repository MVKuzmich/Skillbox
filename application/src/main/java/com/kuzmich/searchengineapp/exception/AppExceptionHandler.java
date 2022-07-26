package com.kuzmich.searchengineapp.exception;

import com.kuzmich.searchengineapp.dto.ResultDTO;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IndexExecutionException.class)
    public ResponseEntity<ResultDTO> handleIndexExecutionException(IndexExecutionException ex, WebRequest webrequest) {

        return new ResponseEntity<>(new ResultDTO(false, ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(EmptySearchQueryException.class)
    public ResponseEntity<ResultDTO> handleIndexExecutionException(EmptySearchQueryException ex, WebRequest webrequest) {
        return new ResponseEntity<>(new ResultDTO(false, ex.getMessage()), HttpStatus.OK);
    }
}
