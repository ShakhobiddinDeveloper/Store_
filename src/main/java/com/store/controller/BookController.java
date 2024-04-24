package com.store.controller;

import com.store.dto.BookDTO;
import com.store.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO dto) {
        log.info("book create{}", dto.getName());
        return ResponseEntity.ok(bookService.create(dto));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id,
                                        @RequestBody BookDTO dto) {
        log.info("book update{}", id);
        return ResponseEntity.ok(bookService.updateById(id, dto));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    //Eng ko'p sotilgan kitoblarni ko'rsatish
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/max-sales")
    public ResponseEntity<?> maxSales() {
        return ResponseEntity.ok(bookService.maxSales());
    }

    //Eng mashxur kitoblar
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @GetMapping("/famous-books")
    public ResponseEntity<?> famousBooks() {
        return ResponseEntity.ok(bookService.famousBooks());
    }
}
