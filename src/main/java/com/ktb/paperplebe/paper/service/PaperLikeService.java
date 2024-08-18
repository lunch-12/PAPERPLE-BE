package com.ktb.paperplebe.paper.service;

import com.ktb.paperplebe.paper.entity.Paper;
import com.ktb.paperplebe.paper.entity.PaperLike;
import com.ktb.paperplebe.paper.repository.PaperLikeRepository;
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
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperLikeService {

    private final PaperLikeRepository paperLikeRepository;
    private final PaperRepository paperRepository;

    public void increaseLikeCount(Long paperId) {
        if (paperLikeRepository.existsByPaperId(paperId)) {
            throw new IllegalStateException("This paper has already been liked.");
        }

        Paper findPaper = paperRepository.findById(paperId).orElseThrow(() -> new NoSuchElementException("Paper not found."));

        PaperLike like = new PaperLike(findPaper);
        findPaper.addLike(like);  // Paper의 좋아요 추가 로직을 호출

        paperLikeRepository.save(like);
    }

    public void decreaseLikeCount(Long paperId) {
        PaperLike like = paperLikeRepository.findByPaperId(paperId).orElseThrow(() -> new IllegalStateException("This paper was not previously liked."));
        Paper findPaper = paperRepository.findById(paperId).orElseThrow(() -> new NoSuchElementException("Paper not found."));

        findPaper.removeLike(like);  // Paper의 좋아요 제거 로직을 호출

        paperLikeRepository.delete(like);
    }

    public Map<Long, Boolean> getLikeStatus(List<Long> paperIds) {
        List<PaperLike> paperLikes = paperLikeRepository.findByPaperIds(paperIds);

        Map<Long, Boolean> result = new HashMap<>();

        paperLikes.forEach(paperLike -> result.put(paperLike.getPaper().getId(), true));
        paperIds.forEach(paperId -> result.putIfAbsent(paperId, false));
        return result;
    }
}