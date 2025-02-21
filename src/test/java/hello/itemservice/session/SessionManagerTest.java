package hello.itemservice.session;

import hello.itemservice.domain.member.Member;
import hello.itemservice.web.session.SessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionManager() {
        // create session
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member("testA", "tester A", "test!");
        sessionManager.createSession(member, response);

        // request
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // find session
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // expire
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}
