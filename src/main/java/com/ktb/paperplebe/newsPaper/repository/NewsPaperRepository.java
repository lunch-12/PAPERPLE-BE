package com.ktb.paperplebe.newsPaper.repository;

import com.ktb.paperplebe.newsPaper.entity.NewsPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface NewsPaperRepository extends JpaRepository<NewsPaper, Long> {

    Optional<NewsPaper> findById(Long id);
}
