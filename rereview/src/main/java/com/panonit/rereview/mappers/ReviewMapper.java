package com.panonit.rereview.mappers;

import com.panonit.rereview.domain.dtos.ReviewCreateUpdateRequestDto;
import com.panonit.rereview.domain.dtos.ReviewDto;
import com.panonit.rereview.domain.entities.Review;
import com.panonit.rereview.domain.entities.ReviewCreateUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewDto toDto(Review review);

    ReviewCreateUpdateRequest toEntity(ReviewCreateUpdateRequestDto requestDto);
}
