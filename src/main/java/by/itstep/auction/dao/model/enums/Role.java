package by.itstep.auction.dao.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER(Set.of(Permission.ITEMS_READ, Permission.ITEMS_WRITE, Permission.LOTS_READ, Permission.LOTS_WRITE,
            Permission.USERS_MONEY, Permission.USERS_READ, Permission.TRANSACTION)),
    ADMIN(Set.of(Permission.ITEMS_WRITE, Permission.ITEMS_READ, Permission.LOTS_READ, Permission.LOTS_WRITE,
            Permission.USERS_READ, Permission.USERS_WRITE, Permission.USERS_MONEY, Permission.TRANSACTION));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
