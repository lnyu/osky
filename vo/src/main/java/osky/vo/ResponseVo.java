package osky.vo;

import osky.Constants;

import java.io.Serializable;

public class ResponseVo implements Serializable {

    private int code;

    private String message;

    private String token;

    private Object data;

    private ResponseVo() {

    }

    public static ResponseVo success() {
        return ResponseVo.create(Constants.ERROR_CODE.SUCCESS);
    }

    public static ResponseVo failure() {
        return ResponseVo.create(Constants.ERROR_CODE.FAILURE);
    }

    public static ResponseVo create(int code) {
        return ResponseVo.create(code, "");
    }


    public static ResponseVo create(int code, String message) {
        return ResponseVo.create(code, message, "");
    }

    public static ResponseVo create(int code, String message, Object data) {
        ResponseVo vo = new ResponseVo();
        vo.setCode(code);
        vo.setMessage(message == null ? "" : message);
        vo.setData(data == null ? "" : data);
        return vo;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
