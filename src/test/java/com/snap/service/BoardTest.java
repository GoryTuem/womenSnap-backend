package com.snap.service;

import com.snap.domain.Board;
import com.snap.domain.Member;
import com.snap.domain.MemberLike;
import com.snap.dto.board.BoardListResponse;
import com.snap.dto.board.UserBoardResponse;
import com.snap.repository.BoardRepository;
import com.snap.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardTest {
    @Autowired private BoardService boardService;
    @Autowired private BoardRepository boardRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private EntityManager em;

    @Test
    public void getUserBoardList() {

        //List<UserBoardResponse> write = boardService.getUserBoardList("WRITE", "thgmlxn@naver.com");
        MemberLike memberLike = new MemberLike();
        memberLike.setBoard(boardRepository.findById(7L).get());
        memberLike.setMember(memberRepository.findById("thgmlxn@naver.com").get());
        em.persist(memberLike);
        em.flush();
        List<UserBoardResponse> write = boardService.getUserBoardList("LIKE", "thgmlxn@naver.com");
    }

    @Test
    public void getBoardList() {

        //List<UserBoardResponse> write = boardService.getUserBoardList("WRITE", "thgmlxn@naver.com");
        MemberLike memberLike = new MemberLike();
        memberLike.setBoard(boardRepository.findById(7L).get());
        memberLike.setMember(memberRepository.findById("thgmlxn@naver.com").get());
        em.persist(memberLike);
        em.flush();
        List<BoardListResponse> write = boardService.getBoardList("", "thgmlxn@naver.com");
    }
}