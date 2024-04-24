package com.store.controller;

import com.store.dto.PurchaseDTO;
import com.store.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    //kitob sotib olish
    @PutMapping
    public ResponseEntity<?> create(@RequestBody PurchaseDTO purchaseDTO) {
        return ResponseEntity.ok(purchaseService.create(purchaseDTO));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestBody PurchaseDTO dto) {
        return ResponseEntity.ok(purchaseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok(purchaseService.deleteById(id));
    }

}
