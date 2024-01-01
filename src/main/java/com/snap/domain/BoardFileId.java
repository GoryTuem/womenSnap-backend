package com.snap.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class BoardFileId implements Serializable {

    @Column(name = "board_id")
    private Long boardId;
    @Column(name = "tag_id")
    private Long tagId;

    public BoardFileId(Long board_id, Long tag_id) {
    }
}
