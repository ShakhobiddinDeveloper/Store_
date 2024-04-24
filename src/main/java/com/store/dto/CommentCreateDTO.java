package com.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreateDTO {
    private String content;
    private String bookId;
    private String replyId;
}
