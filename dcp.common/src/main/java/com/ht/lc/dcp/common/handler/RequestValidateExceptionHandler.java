package com.ht.lc.dcp.common.handler;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.base.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2022-09-20 11:39
 * @Version 1.0
 **/

@RestControllerAdvice
public class RequestValidateExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RequestValidateExceptionHandler.class);

    /**
     * 方法参数校验错误
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultObject handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        String message = "";
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (!CollectionUtils.isEmpty(errors)) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    LOG.error("request parameter wrong : object: " + fieldError.getObjectName() + ", field: " + fieldError.getField() +
                            ", errorMessage: " + fieldError.getDefaultMessage() + ". ");
                });

                FieldError fieldError = (FieldError) errors.get(0);
                message = fieldError.getDefaultMessage();
            }
        }
        return ResultObject.error(ResultCode.SYS_REQ_PARAM_ERROR.getCode(),
                message == "" ? ResultCode.SYS_REQ_PARAM_ERROR.getDesc() : message);
    }

    /**
     * 参数类型转换错误
     *
     * @param exception 错误
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResultObject parameterTypeException(HttpMessageConversionException exception) {
        LOG.error(exception.getCause().getLocalizedMessage());
        return ResultObject.error(ResultCode.SYS_REQ_PARAM_ERROR);
    }
}
