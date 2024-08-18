package com.ktb.paperplebe.paper.fixture;

import com.ktb.paperplebe.paper.dto.PaperRequest;
import com.ktb.paperplebe.paper.dto.PaperResponse;
import com.ktb.paperplebe.paper.entity.Paper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class PaperFixture {

    public static final Long PAPER_ID_1 = 1L;
    public static final Long PAPER_ID_2 = 2L;

    private static final String PAPER_TITLE_1 = "Content 1";
    private static final String PAPER_TITLE_2 = "Content 2";

    private static final String NEWSPAPER_LINK_1 = "http://example.com/article1";
    private static final String NEWSPAPER_LINK_2 = "http://example.com/article2";

    private static final int VIEW_COUNT_1 = 100;
    private static final int VIEW_COUNT_2 = 200;

    private static final String SUMMARY_1 = "Summary 1";
    private static final String SUMMARY_2 = "Summary 2";

    private static final String IMAGE_URL_1 = "http://example.com/image1.jpg";
    private static final String IMAGE_URL_2 = "http://example.com/image2.jpg";

    public static Paper createPaper1() {
        Paper paper = Paper.of(PAPER_TITLE_1, NEWSPAPER_LINK_1, VIEW_COUNT_1, SUMMARY_1, IMAGE_URL_1);
        ReflectionTestUtils.setField(paper, "id", PAPER_ID_1);
        return paper;
    }

    public static Paper createPaper2() {
        Paper paper = Paper.of(PAPER_TITLE_2, NEWSPAPER_LINK_2, VIEW_COUNT_2, SUMMARY_2, IMAGE_URL_2);
        ReflectionTestUtils.setField(paper, "id", PAPER_ID_2);
        return paper;
    }

    public static PaperRequest createPaperRequest1() {
        return new PaperRequest(PAPER_TITLE_1, NEWSPAPER_LINK_1, VIEW_COUNT_1, SUMMARY_1, IMAGE_URL_1);
    }

    public static PaperRequest createPaperRequest2() {
        return new PaperRequest(PAPER_TITLE_2, NEWSPAPER_LINK_2, VIEW_COUNT_2, SUMMARY_2, IMAGE_URL_2);
    }

    public static PaperResponse createPaperResponse1() {
        Paper paper = createPaper1();
        return PaperResponse.of(paper);
    }

    public static PaperResponse createPaperResponse2() {
        Paper paper = createPaper2();
        return PaperResponse.of(paper);
    }

    public static List<PaperResponse> createPaperResponseList() {
        List<PaperResponse> paperResponses = new ArrayList<>();
        paperResponses.add(createPaperResponse1());
        paperResponses.add(createPaperResponse2());
        return paperResponses;
    }
}