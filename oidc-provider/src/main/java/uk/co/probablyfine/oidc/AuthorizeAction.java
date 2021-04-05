package uk.co.probablyfine.oidc;

import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationRequest;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class AuthorizeAction {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizeAction.class);

    static String authorize(Request request, Response response) throws Exception {

        LOG.info("Processing /authorize request");

        var authRequest = AuthenticationRequest.parse(request.queryString());

        var redirectTo = new AuthenticationSuccessResponse(
            authRequest.getRedirectionURI(),
            new AuthorizationCode(),
            null,
            null,
            authRequest.getState(),
            null,
            null
        ).toURI();

        response.redirect(redirectTo.toString());

        return null;
    }
}
