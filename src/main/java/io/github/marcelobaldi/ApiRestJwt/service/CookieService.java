package io.github.marcelobaldi.ApiRestJwt.service;
import io.github.marcelobaldi.ApiRestJwt.dto.support.UserCookieDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CookieService {
    UserCookieDTO user;

    public void setCookie(HttpServletResponse response, Map<String, Object> body){
        Cookie cookieId    = new Cookie("userId",    body.get("id").toString());
        Cookie cookieToken = new Cookie("userToken", body.get("token").toString());

        response.addCookie(cookieId);
        response.addCookie(cookieToken);
    }

    public UserCookieDTO getCookie(String idV, String tokenV){
        if(idV.equals("notFound") || tokenV.equals("notFound")) {
            user = new UserCookieDTO(null, null);
        }else{
            user = new UserCookieDTO(idV, tokenV);
        }
        return user;
    }

    public void deleteCookie(HttpServletResponse response){
        Cookie cookieId    = new Cookie("userId",    null);
        Cookie cookieToken = new Cookie("userToken", null);

        response.addCookie(cookieId);
        response.addCookie(cookieToken);
    }
}
