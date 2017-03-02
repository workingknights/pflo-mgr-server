package name.aknights.core;

import io.dropwizard.auth.Authenticator;
import name.aknights.core.auth.Role;
import name.aknights.core.auth.User;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtContext;

import java.util.Optional;

public class BasicAuthenticator implements Authenticator<JwtContext, User> {

    @Override
    public Optional<User> authenticate(JwtContext context) {
        // Provide your own implementation to lookup users based on the principal attribute in the
        // JWT Token. E.g.: lookup users from a database etc.
        // This method will be called once the token's signature has been verified

        // In case you want to verify different parts of the token you can do that here.
        // E.g.: Verifying that the provided token has not expired.

        // All JsonWebTokenExceptions will result in a 401 Unauthorized response.

        try {
            final String subject = context.getJwtClaims().getSubject();
            Role role;
            if (subject.equals("google-oauth2|118300408301077615291"))
                role = Role.ADMIN;
            else
                role = Role.CLIENT;

            return Optional.of(new User(subject, role));
        }
        catch (MalformedClaimException e) { return Optional.empty(); }
    }
}
