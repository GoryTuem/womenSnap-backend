package com.snap.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARDS")
@Getter @Setter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mem_email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private  Category category;

    @OneToMany(mappedBy = "board")
    private List<BoardFile> boardFiles = new ArrayList<>();

    @OneToMany(mappedBy = "board",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BoardTag> boardTags = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<MemberLike> memberLikes = new ArrayList<>();

    public void addFile(BoardFile boardFile){
        if (!this.boardFiles.contains(boardFile)) {
            this.boardFiles.add(boardFile);
            boardFile.setBoard(this);
        }
    }

    public void addTag(BoardTag boardTag){
        boardTags.add(boardTag);
        boardTag.setParent(this);
    }

    public void addLike(MemberLike memberLike){
        memberLikes.add(memberLike);
        memberLike.setBoard(this);
    }

    private LocalDateTime reg_date;
    private LocalDateTime modify_date;

    public static Board CreateBoard(Member member, Category category) {
        Board board = new Board();
        board.setMember(member);
        board.setCategory(category);
        board.setReg_date(LocalDateTime.now());
        return board;
    }
}

