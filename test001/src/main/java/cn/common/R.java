package cn.common;

import cn.model.entity.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;

public class R<T> implements Serializable {
    private static final long  serialVersionUID=1L;
    private int code;
    private boolean success;
    private T data;
    private String msg;

    public R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success=ResultCode.SUCCESS.code==code;
    }
    public R(ResultCode resultCode,String msg)
    {
        this(resultCode,null,msg);
    }
    public R(ResultCode resultCode,T data,String msg)
    {
        this(resultCode.getCode(),data,msg);
    }

    public static <T> R<T> data(T data)
    {
        return data(data,"操作成功");
    }

    public static <T> R<T> data(T data,String msg)
    {
        return data(200,data,msg);
    }
    public static <T> R<T> data(int code,T data,String msg)
    {
        return new R(code,data,data==null?null:msg);
    }

    public static <T> R<T> success(String msg)
    {
        return new R(ResultCode.SUCCESS,msg);
    }
    public static <T> R<T> fail(String msg)
    {
        return new R(ResultCode.FAILURE,msg);
    }

    public static final Key KEY=new SecretKeySpec("zucc".getBytes(),
            SignatureAlgorithm.HS512.getJcaName());
    public static <T> R<T> loginsuccess(User user){
        JwtBuilder builder= Jwts.builder().setHeaderParam("type","JsonWebToken")
                .claim("id",user.getId())
                .claim("user_name",user.getUser_name())
                .claim("user_role",user.getUser_role())
                .claim("user_leader_id",user.getUser_leader_id())
                .signWith(SignatureAlgorithm.HS512,KEY);
        long expMillis=System.currentTimeMillis()+3600*1000*24;  //下面三行是设置这个token的保存时间
        Date exp =new Date(expMillis);
        builder.setExpiration(exp).setNotBefore(new Date());
        return new R(ResultCode.SUCCESS,builder.compact(),"登录成功");
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }
    public R()
    {

    }
}
