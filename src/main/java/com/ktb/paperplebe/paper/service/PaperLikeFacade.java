package com.daily.daily.paper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaperLikeFacade {

    private final PaperLikeService paperLikeService;

    private void executeWithRetry(Runnable action) throws InterruptedException {
        while (true) {
            try {
                action.run();
                break;
            } catch (OptimisticLockingFailureException e) {
                Thread.sleep(100);
            }
        }
    }

    public void increaseLikeCount(Long paperId) throws InterruptedException {
        executeWithRetry(() -> paperLikeService.increaseLikeCount(paperId));
    }

    public void decreaseLikeCount(Long paperId) throws InterruptedException {
        executeWithRetry(() -> paperLikeService.decreaseLikeCount(paperId));
    }
}
