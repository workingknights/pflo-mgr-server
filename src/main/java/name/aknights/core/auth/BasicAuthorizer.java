package name.aknights.core.auth;

public class BasicAuthorizer implements io.dropwizard.auth.Authorizer<User> {
    @Override
    public boolean authorize(User user, String role) {
        return role.equals(user.getRole().name());
    }
}
