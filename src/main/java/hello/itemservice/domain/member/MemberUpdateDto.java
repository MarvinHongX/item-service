package hello.itemservice.domain.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public MemberUpdateDto() {
    }
}
