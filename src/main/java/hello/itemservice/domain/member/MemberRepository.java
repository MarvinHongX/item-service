package hello.itemservice.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId2(String loginId) {
        return findAll().stream().filter(member -> member.getLoginId().equals(loginId)).findFirst();
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(member -> member.getLoginId() != null && member.getLoginId().equals(loginId)) // NULL 체크 추가
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(MemberUpdateDto memberParamDto) {
        if (store.containsKey(memberParamDto.getId())) {
            Member findMember = findById(memberParamDto.getId());
            findMember.updateMember(memberParamDto);
        }
    }

    public void clearStore() {
        store.clear();
    }
}
