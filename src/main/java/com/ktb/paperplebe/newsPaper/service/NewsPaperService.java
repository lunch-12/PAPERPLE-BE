package com.ktb.paperplebe.newsPaper.service;

import com.ktb.paperplebe.newsPaper.entity.NewsPaper;
import com.ktb.paperplebe.newsPaper.repository.NewsPaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsPaperService {

    private final NewsPaperRepository newsPaperRepository;

    public NewsPaper findById(Long id) {
        return newsPaperRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("NewsPaper with ID " + id + " not found"));
    }

    public List<NewsPaper> findAll() {
        return newsPaperRepository.findAll();
    }
}
