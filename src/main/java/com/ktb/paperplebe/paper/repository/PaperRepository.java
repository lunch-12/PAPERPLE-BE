package com.ktb.paperplebe.paper.repository;

import com.ktb.paperplebe.paper.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}
