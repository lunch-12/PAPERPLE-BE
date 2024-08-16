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

    private Paper(final String content, final String newspaperLink, final int view, final String newspaperSummary, final String image) {
        this.content = content;
        this.newspaperLink = newspaperLink;
        this.view = view;
        this.newspaperSummary = newspaperSummary;
        this.image = image;
    }

    public static Paper of(final String content, final String newspaperLink, final int view, final String newspaperSummary, final String image) {
        return new Paper(content, newspaperLink, view, newspaperSummary, image);
    }

    public void updateContent(final String content) {
        if (content != null && !content.isEmpty()) {
            this.content = content;
        }
    }

    public void updateNewspaperLink(final String newspaperLink) {
        if (newspaperLink != null && !newspaperLink.isEmpty()) {
            this.newspaperLink = newspaperLink;
        }
    }

    public void updateView(final int view) {
        if (view >= 0) {
            this.view = view;
        }
    }

    public void updateNewspaperSummary(final String newspaperSummary) {
        this.newspaperSummary = newspaperSummary;
    }

    public void updateImage(final String image) {
        this.image = image;
    }


}


