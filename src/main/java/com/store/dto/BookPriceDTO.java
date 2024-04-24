package com.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPriceDTO {
    private String id;
    private Double price;
    private String bookId;
}
