package com.store.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookDTO {
    private String id;
    private String name;
    private String authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Boolean visible;

    // kitob necha marotaba sotilgani
    private Long salesCount;

    // commentlar soni
    private Long commentCount;

    // view count
    private Long viewCount;
}
