package de.inmediasp.graphql.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JWTPreAuthenticationToken extends PreAuthenticatedAuthenticationToken {
    @Builder
    public JWTPreAuthenticationToken(final JWTUserDetails principal, final WebAuthenticationDetails details) {
        super(principal, null, principal.getAuthorities());
        super.setDetails(details);
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
