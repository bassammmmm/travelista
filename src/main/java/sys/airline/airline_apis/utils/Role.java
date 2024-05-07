package sys.airline.airline_apis.utils;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static sys.airline.airline_apis.utils.Permissions.*;

public enum Role {
    USER(Collections.emptySet(),"USER"),
    ADMIN(Set.of(
            ADMIN_DELETE,
            ADMIN_CREATE,
            ADMIN_UPDATE,
            ADMIN_READ

    ),"ADMIN");
    @Getter
    private final Set<Permissions> permissions;

    private final String name;

    Role(Set<Permissions> permissions, String name) {
        this.permissions = permissions;
        this.name = name;
    }

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities  = getPermissions()
                .stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.getPermissions()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name));
        return authorities;
    }

}
