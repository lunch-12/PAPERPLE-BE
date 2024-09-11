package com.ktb.paperplebe.newsPaper.dto;

import com.ktb.paperplebe.newsPaper.entity.NewsPaper;
import com.ktb.paperplebe.paper.entity.Paper;
import com.ktb.paperplebe.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record NewsPaperResponse(
        Long newsPaperId,
        String title,
        String link,
        String image,
        String summary,
        LocalDateTime createdAt
) {
    public static NewsPaperResponse of(final NewsPaper newsPaper) {
        return new NewsPaperResponse(
                newsPaper.getId(),
                newsPaper.getTitle(),
                newsPaper.getLink(),
                newsPaper.getImage(),
                newsPaper.getSummary(),
                newsPaper.getCreatedAt()
        );
    }
}
