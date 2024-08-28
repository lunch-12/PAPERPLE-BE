package com.ktb.paperplebe.paper.controller;

import com.ktb.paperplebe.common.dto.SuccessResponse;
import com.ktb.paperplebe.paper.service.PaperLikeFacade;
import com.ktb.paperplebe.paper.service.PaperLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/paper")
@RestController
public class PaperLikeController {

    private final PaperLikeService paperLikeService;
    private final PaperLikeFacade paperLikeFacade;

    @PostMapping("/{paperId}/likes")
    public SuccessResponse increaseLikeCount(
            //@AuthenticationPrincipal Long memberId,
            @PathVariable Long paperId) throws InterruptedException {
        paperLikeFacade.increaseLikeCount(
                //memberId,
                paperId);
        return new SuccessResponse();
    }

    @DeleteMapping("/{paperId}/likes")
    public SuccessResponse decreaseLikeCount(
            //@AuthenticationPrincipal Long memberId,
            @PathVariable Long paperId) throws InterruptedException {
        paperLikeFacade.decreaseLikeCount(
               //memberId,
                paperId);
        return new SuccessResponse();
    }

    @GetMapping("/likes")
    //@Secured(value = "ROLE_MEMBER")
    public Map<Long, Boolean> getLikeStatus(
            //@AuthenticationPrincipal Long memberId,
            @RequestParam("paperIds") List<Long> paperIds) {
        return paperLikeService.getLikeStatus(
                //memberId,
                paperIds);
    }
}
