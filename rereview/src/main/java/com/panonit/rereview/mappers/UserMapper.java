package com.panonit.rereview.mappers;


import com.panonit.rereview.domain.dtos.UserDto;
import com.panonit.rereview.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserDto userDto);
}
