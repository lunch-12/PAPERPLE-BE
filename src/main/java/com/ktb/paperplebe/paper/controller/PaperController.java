package com.ktb.paperplebe.paper.controller;

import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> getPaperList(@RequestParam(defaultValue = "0") final int page,
                                          @RequestParam(defaultValue = "20") final int size,
                                          @RequestParam(defaultValue = "createdAt") final String orderBy) {
        final Pageable pageable = PageRequest.of(page, size);
        final List<PaperResponse> paperListResponse = paperService.getPaperList(pageable, orderBy);
        return ResponseEntity.ok(paperListResponse);
    }

    @PatchMapping("/{paperId}")
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
