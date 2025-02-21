package hello.itemservice.domain.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSaveDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public MemberSaveDto() {
    }
}
