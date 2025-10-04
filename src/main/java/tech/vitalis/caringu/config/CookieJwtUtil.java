package tech.vitalis.caringu.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CookieJwtUtil {

    @Value("${jwt.cookie.name:jwt-token}")
    private String cookieName;

    @Value("${jwt.cookie.max-age:86400}") // 24 horas em segundos
    private int cookieMaxAge;

    @Value("${jwt.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${jwt.cookie.http-only:true}")
    private boolean cookieHttpOnly;

    @Value("${jwt.cookie.same-site:Strict}")
    private String cookieSameSite;

    /**
     * Cria um cookie HTTP-only com o token JWT
     */
    public void createJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(cookieName, token);
        cookie.setMaxAge(cookieMaxAge);
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setSecure(cookieSecure);
        cookie.setPath("/");
        
        // Adiciona o atributo SameSite
        response.addHeader("Set-Cookie", 
            String.format("%s=%s; Max-Age=%d; Path=/; HttpOnly; SameSite=%s%s",
                cookieName, token, cookieMaxAge, cookieSameSite,
                cookieSecure ? "; Secure" : ""));
    }

    /**
     * Remove o cookie JWT
     */
    public void removeJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(cookieHttpOnly);
        cookie.setSecure(cookieSecure);
        
        response.addCookie(cookie);
    }

    /**
     * Extrai o token JWT do cookie da requisição
     */
    public String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Verifica se existe um cookie JWT na requisição
     */
    public boolean hasJwtCookie(HttpServletRequest request) {
        return getJwtFromCookie(request) != null;
    }
}
