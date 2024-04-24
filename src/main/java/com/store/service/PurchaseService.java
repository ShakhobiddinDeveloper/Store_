package com.store.service;

import com.store.dto.PurchaseDTO;
import com.store.entity.PurchaseEntity;
import com.store.exp.AppBadException;
import com.store.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BookPriceService bookPriceService;

    public PurchaseDTO create(PurchaseDTO purchaseDTO) {
        profileService.get(purchaseDTO.getProfileId());
        bookService.get(purchaseDTO.getBookId());
        PurchaseEntity entity = new PurchaseEntity();
        entity.setProfileId(purchaseDTO.getProfileId());
        entity.setBookId(purchaseDTO.getBookId());
        Double price = bookPriceService.get(purchaseDTO.getBookId()).getPrice();
        entity.setPrice(price);
        purchaseRepository.save(entity);
        purchaseDTO.setId(entity.getId());
        profileService.updateProfilePurchasesCount(purchaseDTO.getProfileId());
        profileService.updateProfileAmountPurchases(purchaseDTO.getProfileId(), price);
        return purchaseDTO;
    }

    public PurchaseDTO update(String id, PurchaseDTO dto) {
        PurchaseEntity entity = get(id);
        if (dto.getBookId() != null) {
            Double price = bookPriceService.get(dto.getBookId()).getPrice();
            entity.setPrice(price);
            entity.setBookId(dto.getBookId());
        }
        entity.setProfileId(dto.getProfileId() == null ? entity.getProfileId() : dto.getProfileId());
        purchaseRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public PurchaseEntity get(String id) {
        Optional<PurchaseEntity> entityOptional = purchaseRepository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new AppBadException("Not found");
        }
        return entityOptional.get();
    }

    public Boolean deleteById(String id) {
        purchaseRepository.deleteById(id);
        return true;
    }

}
