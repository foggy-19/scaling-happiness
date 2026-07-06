package com.panonit.restless.mappers.impl;

import com.panonit.restless.domain.dto.BookDto;
import com.panonit.restless.domain.entities.BookEntity;
import com.panonit.restless.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapToDto(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
