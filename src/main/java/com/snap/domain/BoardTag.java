package com.snap.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
public class BoardTag implements Persistable<BoardFileId>{
    @EmbeddedId
    private BoardFileId id;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @CreatedDate
    private LocalDate created;

    public void setParent(Board board) {
        this.board = board;
        this.id = new BoardFileId(board.getId(), tag.getId());
    }
    @Override
    public BoardFileId getId() {
        return id;
    }

    // 새로운 엔티티 판단 전략 재정의
    @Override
    public boolean isNew() {
        return created == null;
    }
}
