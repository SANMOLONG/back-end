package com.innocamp.sanmolong.mountain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseNm;

    @Column(nullable = false)
    private String courseLevel;

    @Column(nullable = false)
    private String spendTime;

    @Column(nullable = false)
    private String coursePhone;

    @Column(nullable = false)
    private String courseImg;

    @Column(nullable = false)
    private String coursePic;

    @Column(nullable = false)
    private String courseDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountainId")
    Mountain mountain;

}
