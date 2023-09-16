package com.innocamp.sanmolong.user.entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private Long point;

    @Column
    private Long age;

    @Column
    private String sex;
}
