package name.aknights;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;

public class JWTSigner {

    /*
     {iss=https://workingknights.auth0.com/,
     sub=google-oauth2|118300408301077615291,
     aud=T2HQUxSUXXutd1rigWpUvdEONkd5s1gm,
     }
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        LocalDate issueLocalDate = LocalDate.of(2017, 1, 1);
        java.util.Date issueDate = java.sql.Date.valueOf(issueLocalDate);

        LocalDate expireLocalDate = LocalDate.of(2017, 3, 1);
        java.util.Date expireDate = java.sql.Date.valueOf(expireLocalDate);

        System.out.println(JWT.create()
                    .withIssuer("https://workingknights.auth0.com/")
                    .withSubject("google-oauth2|118300408301077615291")
                    .withAudience("T2HQUxSUXXutd1rigWpUvdEONkd5s1gm")
                    .withIssuedAt(issueDate)
                    .withExpiresAt(expireDate)
                    .sign(Algorithm.HMAC256("cV2uHolULHblnTjc28BGbahb2UjwqN_iEu5yfgMo22KrOVr_9zeQ2i2zzN6ZwgXL")));
    }
}
