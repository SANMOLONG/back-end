package com.innocamp.sanmolong.global.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {
    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate createdAt;
}
