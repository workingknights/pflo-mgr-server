package name.aknights.module;

import dagger.Module;
import dagger.Provides;
import name.aknights.config.PortfolioManagerConfiguration;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;

import javax.inject.Singleton;
import java.io.UnsupportedEncodingException;

@Module
public class AuthModule {

    private final byte[] tokenSecret;
    private final String expectedAudience;

    public AuthModule(PortfolioManagerConfiguration config) throws UnsupportedEncodingException {
        this.tokenSecret = config.getJwtTokenSecret();
        this.expectedAudience = config.getExpectedAudience();
    }

    @Singleton
    @Provides
    byte[] provideTokenSecret() {
        return tokenSecret;
    }

    @Singleton
    @Provides
    JwtConsumer provideJwtConsumer() {
        return new JwtConsumerBuilder()
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setExpectedAudience(this.expectedAudience)
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setRequireSubject() // the JWT must have a subject claim
                .setVerificationKey(new HmacKey(tokenSecret)) // verify the signature with the public key
                .setRelaxVerificationKeyValidation() // relaxes key length requirement
                .build(); // create the JwtConsumer instance
    }
}