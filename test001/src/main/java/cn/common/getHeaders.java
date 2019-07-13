package cn.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

public class getHeaders {
    public static Long getHeadersId(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long userid=jwt.getBody().get("id",Long.class);
        return userid;
    }
    public static String getHeaderName(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        String username=jwt.getBody().get("user_name",String.class);
        return username;
    }
    public static String getHeaderRole(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        String role=jwt.getBody().get("user_role",String.class);
        return role;
    }
    public static Long getHeaderLeaderId(HttpServletRequest request)
    {
        String token=request.getHeader("token");
        Jws<Claims> jwt= Jwts.parser().setSigningKey(R.KEY).parseClaimsJws(token);
        Long leader_id=jwt.getBody().get("user_leader_id",Long.class);
        return leader_id;
    }
}
