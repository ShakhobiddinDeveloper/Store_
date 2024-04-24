package com.store.service;

import com.store.dto.BookDTO;
import com.store.entity.BookEntity;
import com.store.exp.AppBadException;
import com.store.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ProfileService profileService;

    public BookEntity get(String bookId) {
        Optional<BookEntity> bookEntityOptional = bookRepository.getById(bookId);
        if (bookEntityOptional.isEmpty()) {
            throw new AppBadException("Book not found");
        }
        return bookEntityOptional.get();
    }

    public Boolean create(BookDTO dto) {
        profileService.get(dto.getAuthorId());
        BookEntity entity = new BookEntity();
        entity.setName(dto.getName());
        entity.setAuthorId(dto.getAuthorId());
        bookRepository.save(entity);
        return true;
    }

    public Boolean updateById(String id, BookDTO dto) {
        BookEntity entity = get(id);
        if (dto.getAuthorId() != null) {
            profileService.get(dto.getAuthorId());
            entity.setAuthorId(dto.getAuthorId());
        }
        entity.setName(dto.getName() == null ? entity.getName() : dto.getName());
        bookRepository.save(entity);
        return true;
    }

    public Boolean delete(String id) {
        BookEntity entity = get(id);
        bookRepository.deleteById(entity.getId());
        return true;
    }

    public BookDTO getById(String id) {
        BookEntity entity = get(id);
        BookDTO bookDTO = toDTO(entity);
        return bookDTO;
    }

    public List<BookDTO> getAll() {
        List<BookEntity> entityList = bookRepository.getAll();
        List<BookDTO> dtoList = new LinkedList<>();
        for (BookEntity entity : entityList) {
            BookDTO dto = toDTO(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public BookDTO maxSales() {
        BookEntity entity = bookRepository.maxSales();
        return toDTO(entity);
    }

    private static BookDTO toDTO(BookEntity entity) {
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAuthorId(entity.getAuthorId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public List<BookDTO> famousBooks() {
        List<BookDTO> dtoList = new LinkedList<>();
        dtoList.add(toDTO(bookRepository.maxSalesCount()));
        dtoList.add(toDTO(bookRepository.maxCommentCount()));
        dtoList.add(toDTO(bookRepository.maxViewCount()));
        return dtoList;
    }
}
