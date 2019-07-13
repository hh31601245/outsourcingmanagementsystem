package cn.common;

import cn.model.entity.User;
import cn.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //Object handler是指controller的@Controller注解下的整个方法名
        String token =request.getHeader("token");
        if(!(handler instanceof HandlerMethod))
        {
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //检查是否有PassToken注解，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)) //A.isAnnotationPresent(B.class) 意思是B类型注解是否在A方法上
        {
            PassToken passToken=method.getAnnotation(PassToken.class);
            if(passToken.required())
            {
                return true;
            }

        }

        if(method.isAnnotationPresent(UserLoginToken.class))
        {
            if(token==null)
            {
                throw new InvalidClientException("无token，请重新登录");
            }
            Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
            Long userId=jwt.getBody().get("id",Long.class);
            User user= userService.getUserById(userId);
            if(user==null)
            {
                throw new InvalidClientException("该用户不存在，请重新登录");
            }
            if(jwt.getBody().get("exp", Date.class).before(new Date()))
            {
                throw new InvalidClientException("链接超时，请重新登录");
            }
            //接下来判断权限
//            System.out.println(method.isAnnotationPresent(employer.class));
//            System.out.println(method.isAnnotationPresent(contractor.class));
//            System.out.println(method.isAnnotationPresent(Developer.class));
            if(method.isAnnotationPresent(employer.class))
            {
                this.userRoleValidate(request,method,"发包方");

            }

            else if(method.isAnnotationPresent(contractor.class))
            {
                this.userRoleValidate(request,method,"承包方管理员");

            }

            else if(method.isAnnotationPresent(Developer.class))
            {
                this.userRoleValidate(request,method,"开发人员");

            }
        }
        return true;
    }
    private void userRoleValidate(HttpServletRequest request,Method method,String Role) throws Exception  //判断权限
    {
        String token =request.getHeader("token");
        Jws<Claims> jwt=Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        String userrole=jwt.getBody().get("user_role",String.class);
        if(Role.equals("发包方"))
        {
            employer employers=method.getAnnotation(employer.class);
            if(employers==null)
            {
                throw new Exception("未配置自定义注解");
            }
            String funcCode=employers.value();
            System.out.println(userrole);
            System.out.println(funcCode);
            if(!funcCode.equals(userrole))
            {
                throw new Exception("权限不足");
            }
        }
        if(Role.equals("承包方管理员"))
        {
            contractor contractors=method.getAnnotation(contractor.class);
            if(contractors==null)
            {
                throw new Exception("没配置自定义注解");
            }
            String funcCode=contractors.value();
            System.out.println(userrole);
            System.out.println(funcCode);
            if(!funcCode.equals(userrole))
            {
                throw new Exception("权限不足");
            }
        }
        if(Role.equals("开发人员"))
        {
            Developer developer=method.getAnnotation(Developer.class);
            if(developer==null)
            {
                throw new Exception("没配置自定义注解");
            }
            String funcCode=developer.value();
            System.out.println(userrole);
            System.out.println(funcCode);
            if(!funcCode.equals(userrole))
            {
                throw new Exception("权限不足");
            }
        }
    }
}
