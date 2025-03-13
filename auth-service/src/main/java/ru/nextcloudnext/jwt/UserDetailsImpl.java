package ru.nextcloudnext.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nextcloudnext.model.Role;
import ru.nextcloudnext.model.UserStatus;
import ru.nextcloudnext.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vladimir F. 10.01.2023
 */
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String login;
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String login, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        Set<GrantedAuthority> authoritySet = rolesToGrantedAuthorities(new HashSet<>(user.getRoles()));
        return new UserDetailsImpl(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getUserStatus().equals(UserStatus.ACTIVE),
                authoritySet
        );
    }

    private static Set<GrantedAuthority> rolesToGrantedAuthorities(Set<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
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
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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
