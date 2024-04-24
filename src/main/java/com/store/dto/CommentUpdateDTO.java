package com.store.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentUpdateDTO {
    private String content;
    private String bookId;
    private String replyId;
}
