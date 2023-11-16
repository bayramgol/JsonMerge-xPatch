package com.portal.PortalProject.mapper;

import com.portal.PortalProject.dto.UserDto;
import com.portal.PortalProject.entity.UserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserEntity toUserEntity(UserDto responseDto);
    UserDto toUserDto(UserEntity userEntity);


}
