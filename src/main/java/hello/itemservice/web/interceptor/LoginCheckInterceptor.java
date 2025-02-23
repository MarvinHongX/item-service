package hello.itemservice.web.interceptor;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.session.SessionConst;
import hello.itemservice.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final SessionManager sessionManager;

    public LoginCheckInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // 쿠키 확인
//        Member loginMember = (Member) sessionManager.getSession(request);
        HttpSession session = request.getSession(false);

        Member loginMember = null;
        if (session != null) {
            loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        }

        // 쿠키가 없으면
        if (loginMember == null) {
            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        request.setAttribute("loginMember", loginMember);

        return true;
    }
}
