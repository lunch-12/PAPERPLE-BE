package com.daily.daily.page.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pages")
public class PageLikeController {

    private final PageLikeService pageLikeService;
    private final PageLikeFacade pageLikeFacade;

    @PostMapping("/{pageId}/likes")
    public SuccessResponseDTO increaseLikeCount(
            //@AuthenticationPrincipal Long memberId,
            @PathVariable Long pageId) throws InterruptedException {
        pageLikeFacade.increaseLikeCount(
                //memberId,
                pageId);
        return new SuccessResponseDTO();
    }

    @DeleteMapping("/{pageId}/likes")
    public SuccessResponseDTO decreaseLikeCount(
            //@AuthenticationPrincipal Long memberId,
            @PathVariable Long pageId) throws InterruptedException {
        pageLikeFacade.decreaseLikeCount(
               //memberId,
                pageId);
        return new SuccessResponseDTO();
    }

    @GetMapping("/likes")
    //@Secured(value = "ROLE_MEMBER")
    public Map<Long, Boolean> getLikeStatus(
            //@AuthenticationPrincipal Long memberId,
            @RequestParam("pageIds") List<Long> pageIds) {
        return pageLikeService.getLikeStatus(
                //memberId,
                pageIds);
    }
}
