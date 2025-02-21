package hello.itemservice.web.member;

import hello.itemservice.domain.member.Member;
import hello.itemservice.domain.member.MemberRepository;
import hello.itemservice.domain.member.MemberSaveDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    /**
     * test data
     */
    @PostConstruct
    public void init() {
        MemberSaveDto testADto = new MemberSaveDto();
        testADto.setLoginId("testa");
        testADto.setName("tester A");
        testADto.setPassword("test!");

        Member testA = new Member(testADto.getLoginId(), testADto.getName(), testADto.getPassword());
        memberRepository.save(testA);
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("member") Member member) {
        return "members/addForm";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("member") MemberSaveDto memberParamDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/addForm";
        }

        Member member = new Member(memberParamDto.getLoginId(), memberParamDto.getName(), memberParamDto.getPassword());
        member.saveMember(memberParamDto);
        Member savedMember = memberRepository.save(member);

        return "redirect:/";
    }
}
