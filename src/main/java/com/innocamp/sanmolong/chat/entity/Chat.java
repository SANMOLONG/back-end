package com.innocamp.sanmolong.chat.entity;

import com.innocamp.sanmolong.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Chat extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private Boolean emergency;

    @Column
    private String location;
}
