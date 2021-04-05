package uk.co.probablyfine.oidc;

import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.SignedJWT;

import java.security.SecureRandom;

public interface Utils {

    static SignedJWT signJwt(SignedJWT originalToken) throws Exception {
        var random = new SecureRandom();
        var sharedSecret = new byte[32];

        random.nextBytes(sharedSecret);

        originalToken.sign(new MACSigner(sharedSecret));

        return originalToken;
    }
}
