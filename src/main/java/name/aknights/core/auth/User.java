package name.aknights.core.auth;

import java.security.Principal;

public class User implements Principal {

    private final String userId;
    private Role role;

    public User(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    @Override
    public String getName() {
        return userId;
    }

    public Role getRole() {
        return role;
    }
}