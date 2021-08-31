package study.datajpa.repository;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.MemberRepository;
import study.datajpa.entity.Member;


import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {


    @Autowired
    MemberRepository repository;


    @Test
    public void test(){

        Member member = new Member("memberA");
        Member saveMember= repository.save(member);

        Member findMember = repository.findById(saveMember.getId()).get();
        
        
        //검증하기
        assertThat(findMember.getId()).isEqualTo(member.getId());  //assertions core
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

    }


}
