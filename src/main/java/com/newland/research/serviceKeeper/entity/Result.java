package com.newland.research.serviceKeeper.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用Rest请求返回结构
 *
 * @author shenhang
 * @date 2020-12-24
 */
@NoArgsConstructor
@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Object data;

    public static final int SUCCESS = 200;
    public static final int UNKNOWN_ERROR = 499;
    public static final int FORBIDDEN_ERROR = 403;
    public static final int TIMEOUT_ERROR = 502;



    //操作成功 调用
    public static Result ok() {
        Result r = new Result();
        r.setSuccess(true);
        r.setCode(SUCCESS);
        r.setData(new HashMap<>());
        r.setMessage("操作成功");
        return r;
    }

    public static Result error() {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(FORBIDDEN_ERROR);
        r.setData(new HashMap<>());
        r.setMessage("操作失败");
        return r;
    }

    public static Result error(Object data,String msg) {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(UNKNOWN_ERROR);
        r.setData(data);
        r.setMessage(msg);
        return r;
    }

    public static Result timeOutError(){
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(TIMEOUT_ERROR);
        r.setData(new HashMap<>());
        r.setMessage("请求超时");
        return r;
    }

    public static Result error(String msg) {
        Result r = new Result();
        r.setSuccess(false);
        r.setCode(UNKNOWN_ERROR);
        r.setData(new HashMap<>());
        r.setMessage(msg);
        return r;
    }



    /**
     * 以下方法为了链式编程
     *
     * @param success
     * @return
     */
    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }


    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    //返回操作成功
    public static Result ok(Object data) {
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setSuccess(true);
        result.setMessage("成功");
        if(data==null) {
            result.setData(new HashMap<>());
        }else{
            result.setData(data);

        }
        return result;
    }

    //返回错误信息
    public static Result error(Exception e) {
        Result result = new Result();
        result.setCode(500);
        result.setSuccess(false);
        result.setMessage(e.getMessage());
        return result;
    }
    //返回错误信息
    public static Result error(Exception e,Integer code) {
        Result result = new Result();
        if(code==null) {
            result.setCode(500);
        }else{
            result.setCode(code);
        }
        result.setSuccess(false);
        result.setMessage(e.getMessage());
        return result;
    }

}