package hello.itemservice.web.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    public void createSession(Object value, HttpServletResponse response) {
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        Cookie cookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(cookie);
    }

    public Object getSession(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if (cookie != null) {
            return sessionStore.get(cookie.getValue());
        }
        return null;
    }

    public void expire(HttpServletRequest request) {
        Cookie cookie = findCookie(request, SESSION_COOKIE_NAME);
        if (cookie != null) {
            sessionStore.remove(cookie.getValue());
        }
    }

    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (getCookies(request) == null) {
            return null;
        }
        return Arrays.stream(getCookies(request)).filter(cookie -> cookie.getName().equals(cookieName)).findAny().orElse(null);
    }

    private static Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }
}
