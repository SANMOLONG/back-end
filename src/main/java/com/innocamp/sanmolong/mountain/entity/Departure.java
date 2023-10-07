package com.innocamp.sanmolong.mountain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Departure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String departNm;

    @Column(nullable = false)
    private String departAd;

    @Column(nullable = false)
    private double startLatitude;

    @Column(nullable = false)
    private double startLongitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courseId")
    Course course;
}
