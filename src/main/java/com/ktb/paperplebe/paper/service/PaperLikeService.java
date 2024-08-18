package com.ktb.paperplebe.paper.service;

import com.ktb.paperplebe.paper.entity.Paper;
import com.ktb.paperplebe.paper.entity.PaperLike;
import com.ktb.paperplebe.paper.repository.PaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

package com.daily.daily.post.service;

import com.daily.daily.member.domain.Member;
import com.daily.daily.member.exception.MemberNotFoundException;
import com.daily.daily.member.repository.MemberRepository;
import com.daily.daily.post.domain.HotPost;
import com.daily.daily.post.domain.Post;
import com.daily.daily.post.domain.PostLike;
import com.daily.daily.post.exception.AlreadyLikeException;
import com.daily.daily.post.exception.NotPreviouslyLikedException;
import com.daily.daily.post.exception.PostNotFoundException;
import com.daily.daily.post.repository.HotPostRepository;
import com.daily.daily.post.repository.PostLikeRepository;
import com.daily.daily.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperLikeService {

    private final PaperLikeRepository paperLikeRepository;
    private final PaperRepository paperRepository;

    public void increaseLikeCount(Long paperId) {
        if (paperLikeRepository.existsByPaperId(paperId)) {
            throw new AlreadyLikeException();
        }

        Paper findPaper = paperRepository.findById(paperId).orElseThrow(PaperNotFoundException::new);

        findPaper.increaseLikeCount();
        paperRepository.saveAndFlush(findPaper); // 낙관적 락 예외 발생 가능성 존재

        saveLikeHistory(findPaper);
    }

    private void saveLikeHistory(Paper findPaper) {
        PaperLike like = PaperLike.builder()
                .paper(findPaper)
                .build();

        paperLikeRepository.save(like);
    }

    public void decreaseLikeCount(Long paperId) {
        PaperLike like = paperLikeRepository.findByPaperId(paperId).orElseThrow(NotPreviouslyLikedException::new);
        Paper findPaper = paperRepository.findById(paperId).orElseThrow(PaperNotFoundException::new);

        findPaper.decreaseLikeCount();
        paperRepository.saveAndFlush(findPaper); // 낙관적 락 예외 가능성 존재
        paperLikeRepository.delete(like);
    }

    public Map<Long, Boolean> getLikeStatus(List<Long> paperIds) {
        List<PaperLike> paperLikes = paperLikeRepository.findByPaperIds(paperIds);

        Map<Long, Boolean> result = new HashMap<>();

        paperLikes.forEach(paperLike -> result.put(paperLike.getPaperId(), true));
        paperIds.forEach(paperId -> result.putIfAbsent(paperId, false));
        return result;
    }
}