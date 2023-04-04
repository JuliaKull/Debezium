package com.knits.enterprise.dto.data.security;

import com.knits.enterprise.dto.data.company.AbstractActiveDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder(toBuilder=true)
public class UserDto extends AbstractActiveDto implements UserDetails {

    @EqualsAndHashCode.Include
    private Long id;
    @EqualsAndHashCode.Include
    private String username;

    @ToString.Exclude
    private String password;

    @EqualsAndHashCode.Include
    private String firstName;

    @EqualsAndHashCode.Include
    private String lastName;

    private String avatar;
    private String countryIso2;
    private String token;
    private boolean expired;
    private boolean locked;
    private boolean credentialExpired;
    private boolean enabled;

    private String email;
    private String roleName;

    private Set<? extends GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities==null){
            authorities= new HashSet<>();
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !credentialExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !locked;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}