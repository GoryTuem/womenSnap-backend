package com.snap.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.snap.dto.member.MemberDto;
import com.snap.repository.MemberRepository;
import com.snap.service.MemberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    EntityManager em;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Before
    public void addProc() {

        MemberDto.CreateMemberRequest request = new MemberDto.CreateMemberRequest();
        request.setMem_email("thgmlxn@naver.com");
        request.setMem_name("소희");
        request.setMem_nickname("sohee_95");
        request.setMem_phone("01077608253");
        request.setMem_password("aA91034658");

        String mem_email = memberService.join(request);
        Optional<Member> fmember = memberRepository.findById(mem_email);
        em.persist(new Tag("어그부츠"));

        Category category = new Category();
        category.setSort(1);
        category.setCate_name("아우터");
        em.persist(category);

        Board board = Board.CreateBoard(fmember.get(), category);
        em.persist(board);

        Board board2 = Board.CreateBoard(fmember.get(), category);
        em.persist(board2);
    }

    @Test
    public void findUserInfo(){
        memberService.findMember("thgmlxn@naver.com");

    }

/*
    @Test
    public void categoryInsert(){
        Category category1 = new Category();
        category1.setSort(1);
        category1.setCate_name("아우터");
        em.persist(category1);
        
        Category category = new Category();
        category.setSort(2);
        category.setCate_name("상의");
        em.persist(category);

        Category category2 = new Category();
        category2.setSort(3);
        category2.setCate_name("하의");
        em.persist(category2);

        Category category3 = new Category();
        category3.setSort(4);
        category3.setCate_name("신발");
        em.persist(category3);
        em.flush();
    }
*/


}