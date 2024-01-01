package com.snap.repository;

import com.snap.domain.Board;
import com.snap.domain.Category;
import com.snap.domain.Member;
import com.snap.repository.queryDSL.BoardRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
