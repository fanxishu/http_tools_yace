package com.newland.research.serviceKeeper.config.handler;

import com.newland.research.serviceKeeper.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result exceptionHandler(HttpServletRequest req,BindException e)
    {
        String url = req.getRequestURI();
        log.error("request error at " + url, e);
        Result result = new Result();
        e.printStackTrace();
        String message= e.getBindingResult().getFieldError().getDefaultMessage();
        result.setCode(HttpStatus.BAD_REQUEST.value());
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }
}
