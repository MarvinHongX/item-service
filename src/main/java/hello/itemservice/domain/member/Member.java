package hello.itemservice.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Member {
    @Setter private Long id;
    private String loginId;
    private String name;
    private String password;

    public Member(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public void saveMember(MemberSaveDto memberParamDto) {
        if (memberParamDto.getLoginId() != null) {
            this.loginId = memberParamDto.getLoginId();
        }
        if (memberParamDto.getName() != null) {
            this.name = memberParamDto.getName();
        }
        if (memberParamDto.getPassword() != null) {
            this.password = memberParamDto.getPassword();
        }
    }

    public void updateMember(MemberUpdateDto memberParamDto) {
        if (memberParamDto.getName() != null) {
            this.name = memberParamDto.getName();
        }
        if (memberParamDto.getPassword() != null) {
            this.password = memberParamDto.getPassword();
        }
    }

}
