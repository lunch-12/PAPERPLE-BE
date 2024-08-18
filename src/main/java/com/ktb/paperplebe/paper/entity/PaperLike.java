package com.ktb.paperplebe.paper.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class PaperLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paper_id")
    private Paper paper;

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @Builder
    public PaperLike(Paper paper
            //, Member member
    ) {
        this.paper = paper;
        //this.member = member;
    }

    protected PaperLike() {
    }

    public Long getPaperId() {
        return paper.getId();
    }
}

