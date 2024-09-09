package com.ktb.paperplebe.paper.service;

import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.entity.Paper;
import com.ktb.paperplebe.paper.exception.PaperException;
import com.ktb.paperplebe.paper.repository.PaperRepository;
import com.ktb.paperplebe.user.entity.User;
import com.ktb.paperplebe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ktb.paperplebe.paper.exception.PaperErrorCode.PAPER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PaperService {
    private final PaperRepository paperRepository;
    private final UserService userService;

    @Transactional
    public PaperResponse createPaper(PaperRequest paperRequest, Long userId) {
        User user = userService.findById(userId);

        Paper paper = Paper.of(
                paperRequest.content(),
                paperRequest.newspaperLink(),
                paperRequest.view(),
                paperRequest.newspaperSummary(),
                paperRequest.image(),
                user
        );

        Paper savedPaper = paperRepository.save(paper);
        return PaperResponse.of(savedPaper);
    }

    @Transactional
    public PaperResponse updatePaper(Long paperId, PaperRequest paperRequest) {
        Paper paper = findPaperByIdOrThrow(paperId);

        paper.updateContent(paperRequest.content());
        paper.updateNewspaperLink(paperRequest.newspaperLink());
        paper.updateView(paperRequest.view());
        paper.updateNewspaperSummary(paperRequest.newspaperSummary());
        paper.updateImage(paperRequest.image());

        paperRepository.save(paper);
        return PaperResponse.of(paper);
    }

    public PaperResponse getPaper(Long paperId) {
        Paper paper = findPaperByIdOrThrow(paperId);
        return PaperResponse.of(paper);
    }

    @Transactional
    public void deletePaper(Long paperId) {
        if (!paperRepository.existsById(paperId)) {
            throw new PaperException(PAPER_NOT_FOUND);
        }
        paperRepository.deleteById(paperId);
    }

    private Paper findPaperByIdOrThrow(Long paperId) {
        return paperRepository.findById(paperId)
                .orElseThrow(() -> new PaperException(PAPER_NOT_FOUND));
    }

}
