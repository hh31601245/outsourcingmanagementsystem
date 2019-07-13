package cn.common;

public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAILURE(400,"服务异常"),
    UN_AUTHORIZED(401,"请求未通过"),
    NOT_FIND(404,"404 没找到请求");
    final int code;
    final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCode(final int code,final String message) {
        this.code = code;
        this.message = message;
    }
}
