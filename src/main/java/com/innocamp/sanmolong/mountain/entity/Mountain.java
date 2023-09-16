package com.innocamp.sanmolong.mountain.entity;

import com.innocamp.sanmolong.badge.entity.Badge;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Mountain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mountain;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String courseLevel;

    @Column(nullable = false)
    private String spendTime;

    @Column(nullable = false)
    private String coursePhone;

    @Column(nullable = false)
    private String courseImg;

    @Column(nullable = false)
    private String departNm;

    @Column(nullable = false)
    private String departAd;

    @OneToOne(mappedBy = "mountain_id")
    private Badge badge;
}
