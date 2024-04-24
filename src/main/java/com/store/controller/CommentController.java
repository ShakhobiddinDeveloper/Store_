package com.store.controller;

import com.store.dto.CommentCreateDTO;
import com.store.dto.CommentDTO;
import com.store.dto.CommentFilterDTO;
import com.store.dto.CommentUpdateDTO;
import com.store.service.CommentService;
import com.store.util.SpringSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentCreateDTO dto) {
        String id = SpringSecurityUtil.getCurrentUser().getId();
        log.info("Comment create{}", id);
        return ResponseEntity.ok(commentService.create(dto, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateById(@RequestBody CommentUpdateDTO dto,
                                                 @PathVariable String id) {
        return ResponseEntity.ok(commentService.update(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<List<CommentDTO>> getByBookId(@PathVariable String bookId) {
        return ResponseEntity.ok(commentService.getByBookId(bookId));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/pagination")
    public ResponseEntity<PageImpl<CommentDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(commentService.pagination(page - 1, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<PageImpl<CommentDTO>> filter(@RequestBody CommentFilterDTO dto,
                                                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size", defaultValue = "5") Integer size) {
        return ResponseEntity.ok(commentService.filter(dto, page - 1, size));
    }


}
