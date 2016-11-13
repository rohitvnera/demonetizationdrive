package com.ampdev.platform.framework.rest.security;

import com.ampdev.platform.module.tictactoe.dataobject.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class UserContext implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = -1639375965626592825L;

    private User userData;
    private boolean isAccountNonExpired = true;
    private boolean isAccountLocked = true;
    private boolean isCredentialNotExpired = true;

    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        return userData.getPassword();
    }

    @Override
    public String getUsername() {
        return userData.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && o instanceof UserContext && Objects.equals(userData, ((UserContext) o).userData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userData);
    }

    @Override
    public String toString() {
        return "UserContext{" + "user=" + userData + '}';
    }

}
