package com.innocamp.sanmolong.mountain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Mountain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mountainNm;
}
