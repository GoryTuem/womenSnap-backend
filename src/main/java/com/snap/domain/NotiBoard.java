package com.snap.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class NotiBoard {
    @Id
    @GeneratedValue
    @Column(name = "noti_id")
    private Long id;
}
