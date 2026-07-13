package com.panonit.rereview.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRangeDto {

    @NotBlank(message = "Open time must be provided")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Open time must be in HH:mm format")
    private String openTime;

    @NotBlank(message = "Close time must be provided")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Close time must be in HH:mm format")
    private String closeTime;
}
