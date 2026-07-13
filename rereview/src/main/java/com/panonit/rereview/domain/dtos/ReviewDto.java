package com.panonit.rereview.domain.dtos;

import com.panonit.rereview.domain.entities.Photo;
import com.panonit.rereview.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private String id;

    private String content;

    private Integer rating;

    private LocalDateTime datePosted;

    private LocalDateTime lastEdited;

    private UserDto writtenBy;

    private List<PhotoDto> photos = new ArrayList<>();
}
