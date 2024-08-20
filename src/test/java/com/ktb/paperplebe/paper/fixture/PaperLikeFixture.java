package com.ktb.paperplebe.paper.fixture;

import java.util.HashMap;
import java.util.Map;

public class PaperLikeFixture {

    public static final Long PAPER_ID_1 = 1L;
    public static final Long PAPER_ID_2 = 2L;

    public static Map<Long, Boolean> createLikeStatus() {
        Map<Long, Boolean> likeStatus = new HashMap<>();
        likeStatus.put(PAPER_ID_1, true);
        likeStatus.put(PAPER_ID_2, false);
        return likeStatus;
    }
}
