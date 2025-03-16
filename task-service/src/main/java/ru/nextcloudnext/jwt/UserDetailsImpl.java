package ru.nextcloudnext.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String login;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String login, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Long id, String login, Set<String> authorities) {
        Set<GrantedAuthority> authoritySet = rolesToGrantedAuthorities(new HashSet<>(authorities));
        return new UserDetailsImpl(
                id,
                login,
                authoritySet
        );
    }

    private static Set<GrantedAuthority> rolesToGrantedAuthorities(Set<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(id, that.id) && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login);
    }
}
