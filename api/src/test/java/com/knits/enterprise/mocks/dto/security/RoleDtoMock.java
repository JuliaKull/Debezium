package com.knits.enterprise.mocks.dto.security;


import com.knits.enterprise.dto.data.security.RoleDto;

public class RoleDtoMock {
    public static RoleDto shallowRoleDto(Long id) {
        return RoleDto.builder()
                .id(id)
                .name("Mock Role"+id)
                .build();
    }
}
