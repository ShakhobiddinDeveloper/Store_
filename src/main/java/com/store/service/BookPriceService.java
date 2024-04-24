package com.store.service;

import com.store.dto.BookPriceDTO;
import com.store.entity.BookPriceEntity;
import com.store.exp.AppBadException;
import com.store.repository.BookPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookPriceService {
    @Autowired
    private BookPriceRepository bookPriceRepository;
    @Autowired
    private BookService bookService;

    public BookPriceDTO create(BookPriceDTO dto) {
        bookService.get(dto.getBookId());
        BookPriceEntity bookPriceEntity = new BookPriceEntity();
        bookPriceEntity.setBookId(dto.getBookId());
        bookPriceEntity.setPrice(dto.getPrice());
        bookPriceRepository.save(bookPriceEntity);
        dto.setId(bookPriceEntity.getId());
        return dto;
    }

    public BookPriceDTO update(BookPriceDTO dto, String id) {
        BookPriceEntity bookPriceEntity = get(id);
        bookPriceEntity.setBookId(dto.getBookId() == null ? bookPriceEntity.getBookId() : dto.getBookId());
        bookPriceEntity.setPrice(dto.getPrice() == null ? bookPriceEntity.getPrice() : dto.getPrice());
        bookPriceRepository.save(bookPriceEntity);
        return toDTO(bookPriceEntity);
    }

    private BookPriceDTO toDTO(BookPriceEntity bookPriceEntity) {
        BookPriceDTO bookPriceDTO = new BookPriceDTO();
        bookPriceDTO.setId(bookPriceEntity.getId());
        bookPriceDTO.setBookId(bookPriceEntity.getBookId());
        bookPriceDTO.setPrice(bookPriceEntity.getPrice());
        return bookPriceDTO;
    }

    public BookPriceEntity get(String id) {
        Optional<BookPriceEntity> entityOptional = bookPriceRepository.getById(id);
        if (entityOptional.isEmpty()) {
            throw new AppBadException("Not found");
        }
        return entityOptional.get();
    }

    public Boolean delete(String id) {
        get(id);
        bookPriceRepository.deleteById(id);
        return true;
    }

    public BookPriceDTO getPriceByBookId(String bookId) {
        BookPriceEntity entity = bookPriceRepository.findByBookId(bookId);
        BookPriceDTO dto = new BookPriceDTO();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setBookId(entity.getBookId());
        return dto;
    }
}
