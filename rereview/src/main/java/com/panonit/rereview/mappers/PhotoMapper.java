package com.panonit.rereview.mappers;


import com.panonit.rereview.domain.dtos.PhotoDto;
import com.panonit.rereview.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoDto toDto(Photo photo);
}
