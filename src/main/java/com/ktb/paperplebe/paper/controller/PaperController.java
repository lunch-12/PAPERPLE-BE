package com.ktb.paperplebe.paper.controller;

import com.ktb.paperplebe.paper.dto.PaperRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/paper")
@RestController
public class PaperController {
    private final PaperService paperService;

    @PostMapping
    public ResponseEntity<?> createPaper(@RequestBody final PaperRequest paperRequest) {
        final PaperResponse paperResponse = paperService.createPaper(paperRequest);
        return ResponseEntity.ok(paperResponse);
    }

    @GetMapping("/{paperId}")
    public ResponseEntity<?> getPaper(@PathVariable Long paperId) {
        final PaperResponse paperResponse = paperService.getPaper(paperId);
        return ResponseEntity.ok(paperResponse);
    }

    @PatchMapping("/{paperId}/edit")
    public ResponseEntity<?> updateRoom(
            @PathVariable Long paperId,
            @RequestBody final PaperRequest paperRequest) {
        final PaperResponse paperResponse = paperService.updatePaper(paperId, paperRequest);
        return ResponseEntity.ok(paperResponse);
    }

    @DeleteMapping("/{paperId}")
    public ResponseEntity<String> deletePaper(@PathVariable Long paperId) {
        paperService.deletePaper(paperId);
        return ResponseEntity.ok("Paper successfully deleted");
    }
}
