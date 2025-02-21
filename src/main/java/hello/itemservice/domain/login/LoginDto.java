package hello.itemservice.domain.login;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;
}
