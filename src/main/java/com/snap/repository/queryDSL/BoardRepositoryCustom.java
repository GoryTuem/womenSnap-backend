package com.snap.repository.queryDSL;

import com.snap.domain.Board;
import com.snap.domain.Tag;
import com.snap.dto.board.BoardListResponse;
import com.snap.dto.board.UserBoardResponse;

import java.util.List;

public interface BoardRepositoryCustom {
    List<UserBoardResponse> findMemberBoard(String mem_email,Long pageNum);
    List<UserBoardResponse> findMemberLike(String mem_email,Long pageNum);
    List<Tag> findTopTagList();
    List<BoardListResponse> getBoardList(Long[] tagId, String mem_email);
}
