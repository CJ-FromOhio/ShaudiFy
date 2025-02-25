package hezix.org.shaudifydemo1.entity.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,ROLE_ADMIN,ROLE_AUTHOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
