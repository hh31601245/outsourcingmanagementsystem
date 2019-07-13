package cn.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED) //用在异常里，增加可读性的， HttpStatus.UNAUTHORIZED是无权访问的错误
public class InvalidClientException extends RuntimeException{
    public InvalidClientException(String msg)
    {
        super(msg);
    }
}
