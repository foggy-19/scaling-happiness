package com.panonit.rereview.domain.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateUpdateRequestDto {

    @NotBlank(message = "Review content is required")
    private String content;

    @NotNull(message = "Review rating is required")
    @Min(value = 1, message = "Review rating must be at least {value}")
    @Max(value = 5, message = "Review rating must be at most {value}")
    private Integer rating;

    private List<String> photoIds;
}
