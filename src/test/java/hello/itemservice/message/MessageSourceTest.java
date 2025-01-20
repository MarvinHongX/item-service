package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void helloMessage() {
        String result = messageSource.getMessage("hello", null, null);
        assertThat(result).isEqualTo("hi");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> messageSource.getMessage("notFound", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = messageSource.getMessage("notFound", null, "default message", null);
        assertThat(result).isEqualTo("default message");
    }

    @Test
    void argumentMessage() {
        String result = messageSource.getMessage("hello.name", new Object[]{"Message"}, null);
        assertThat(result).isEqualTo("hi Message");
    }

    @Test
    void defaultLang() {
        assertThat(messageSource.getMessage("hello", null, null)).isEqualTo("hi");
        assertThat(messageSource.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hi");
    }

    @Test
    void koLang() {
        assertThat(messageSource.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }
}
