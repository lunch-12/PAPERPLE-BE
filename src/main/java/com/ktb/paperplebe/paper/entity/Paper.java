package com.ktb.paperplebe.paper.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Paper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "newspaper_link", nullable = false, length = 255)
    private String newspaperLink;

    @Column(name = "view", nullable = false)
    private int view;

    @Column(name = "newspaper_summary", nullable = true, length = 255)
    private String newspaperSummary;

    @Column(name = "image", nullable = true, length = 255)
    private String image;
}


