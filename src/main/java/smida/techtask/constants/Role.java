package smida.techtask.constants;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = "ROLE" + value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
