package com.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentFilterDTO {
    private String id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String profileId;
    private String content;
    private String bookId;
    private String replyId;
    private Boolean visible;
}
