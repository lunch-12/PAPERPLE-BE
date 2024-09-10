package com.ktb.paperplebe.newsPaper.controller;

import com.ktb.paperplebe.newsPaper.entity.NewsPaper;
import com.ktb.paperplebe.newsPaper.service.NewsPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news-paper")
public class NewsPaperController {

    private final NewsPaperService newsPaperService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsPaper> getNewsPaperById(@PathVariable Long id) {
        NewsPaper newsPaper = newsPaperService.findById(id);
        return ResponseEntity.ok(newsPaper);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsPaper>> getAllNewsPaper() {
        List<NewsPaper> newsPapers = newsPaperService.findAll();
        return ResponseEntity.ok(newsPapers);
    }
}
