package com.ktb.paperplebe.paper.exception;

import com.ktb.paperplebe.common.exception.DomainException;

public class PaperLikeException extends DomainException {
    public PaperLikeException(final PaperErrorCode errorCode)  {
        super(errorCode);
    }
}
