package com.ktb.paperplebe.paper.dto;

public record PaperRequest(
        String content,
        String newspaperLink,
        int view,
        String newspaperSummary,
        String image
) {
}