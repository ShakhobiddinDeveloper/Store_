package com.store.controller;

import com.store.dto.BookPriceDTO;
import com.store.service.BookPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-price")
public class BookPriceController {
    @Autowired
    private BookPriceService bookPriceService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookPriceDTO dto) {
        return ResponseEntity.ok(bookPriceService.create(dto));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestBody BookPriceDTO dto) {
        return ResponseEntity.ok(bookPriceService.update(dto, id));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok(bookPriceService.delete(id));
    }
    // book id buyica narxini olish
    @GetMapping("/{bookId}")
    public ResponseEntity<?> getPriceByBookId(@PathVariable String bookId) {
        return ResponseEntity.ok(bookPriceService.getPriceByBookId(bookId));
    }

}
