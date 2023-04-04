package com.knits.enterprise.mapper.security;

import com.knits.enterprise.model.security.User;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.mapper.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface UserMapper extends EntityMapper<User, UserDto> {


//    @Override
//    default User toEntity (UserDto user) {
//        return null;
//    }

    @Override
    default UserDto toDto(User authUserAsEntity) {

        Set<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(authUserAsEntity.getRole().getName()));

        return UserDto.builder()
                .avatar(authUserAsEntity.getAvatarUrl())
                .id(authUserAsEntity.getId())
                .password(authUserAsEntity.getPassword()) //necessary for validation in
                .credentialExpired(authUserAsEntity.isCredentialExpired())
                .enabled(authUserAsEntity.isActive())
                .expired(authUserAsEntity.isExpired())
                .active(authUserAsEntity.isActive())
                .locked(authUserAsEntity.isLocked())
                .authorities(authorities)
                .roleName(authUserAsEntity.getRole().getName())
                .username(authUserAsEntity.getUsername())
                .build();
    }

}