package cn.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE}) //注解的作用目标，METhOD方法，TYPE 类，借口
@Retention(RetentionPolicy.RUNTIME) //注解的持续时间
public @interface UserLoginToken {
    boolean required() default true;
}
