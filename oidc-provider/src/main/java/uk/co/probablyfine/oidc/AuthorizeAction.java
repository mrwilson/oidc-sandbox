package uk.co.probablyfine.oidc;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static uk.co.probablyfine.oidc.Utils.signJwt;

public class AuthorizeAction {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizeAction.class);

    static String authorize(Request request, Response response) throws Exception {

        LOG.info("Processing /authorize request");

        var authRequest = AuthenticationRequest.parse(request.queryString());

        var redirectTo = new AuthenticationSuccessResponse(
            authRequest.getRedirectionURI(),
            makeCode(authRequest),
            makeIdToken(authRequest),
            null,
            authRequest.getState(),
            null,
            null
        ).toURI();

        response.redirect(redirectTo.toString());

        return "";
    }

    private static AuthorizationCode makeCode(AuthenticationRequest authRequest) {
        return authRequest.getResponseType().impliesCodeFlow()
                ? new AuthorizationCode()
                : null;
    }

    private static SignedJWT makeIdToken(AuthenticationRequest authRequest) throws Exception {
        if (!authRequest.getResponseType().impliesImplicitFlow()) {
            return null;
        }

        var claimsSetBuilder = new JWTClaimsSet.Builder()
                .subject(UUID.randomUUID().toString())
                .issuer("Login-a-tron")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000));

        claims().forEach(claimsSetBuilder::claim);

        return signJwt(new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSetBuilder.build()));
    }

    private static Map<String, String> claims() {
        return ofEntries(
            entry("name", "Foo Bar"),
            entry("given_name", "Foo"),
            entry("family_name", "Bar"),
            entry("email", "foo.bar@login.a.tron")
        );
    }
}
