package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testMember() {
        Member member = new Member();
        member.setName("memberA");

        Long saveId = memberRepository.save(member);
        Member member1 = memberRepository.find(saveId);

        assertThat(member1.getId()).isEqualTo(member.getId());
        assertThat(member1.getName()).isEqualTo(member.getName());
        assertThat(member1).isEqualTo(member);
    }
}