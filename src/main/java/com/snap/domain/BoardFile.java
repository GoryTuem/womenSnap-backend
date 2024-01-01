package com.snap.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class BoardFile{

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    private String fileName;
    private String filePath;
    private int sort;
    private LocalDateTime reg_date;

}
