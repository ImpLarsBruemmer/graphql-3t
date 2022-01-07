package de.inmediasp.graphql.security;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final JWTVerifier verifier;
    private final Algorithm algorithm;
    private final UserRepository repository;

    @Transactional
    public JWTUserDetails loadUserByToken(final String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(repository::findByEmail)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(BadTokenException::new);
    }

    private Optional<DecodedJWT> getDecodedToken(final String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch (final JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return repository
                .findByEmail(email)
                .map(user -> getUserDetails(user, getToken(user)))
                .orElseThrow(() -> new UsernameNotFoundException("Username or password didn't match"));
    }

    public String getToken(final User user) {
        final Instant now = Instant.now();
        final Instant expiry = Instant.now().plus(Duration.ofHours(4));
        return JWT
                .create()
                .withIssuer("my-graphql-api")
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getEmail())
                .sign(algorithm);
    }

    private JWTUserDetails getUserDetails(final User user, final String token) {
        return JWTUserDetails
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
                .token(token)
                .build();
    }

    public User getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(repository::findByEmail)
                .orElse(null);
    }
}
