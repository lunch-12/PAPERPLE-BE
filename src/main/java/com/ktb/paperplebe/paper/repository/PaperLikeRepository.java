package com.ktb.paperplebe.paper.repository;

import com.ktb.paperplebe.paper.entity.PaperLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaperLikeRepository extends JpaRepository<PaperLike, Long> {

    @Query("select pl from PaperLike pl where pl.paper.id = :paperId")
    Optional<PaperLike> findByPaperId(Long paperId);

    @Query("select pl from PaperLike pl where pl.paper.id in :paperIds")
    List<PaperLike> findByPaperIds(List<Long> paperIds);

    boolean existsByPaperId(Long paperId);
}
