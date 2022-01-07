package de.inmediasp.graphql.operations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

import de.inmediasp.graphql.security.User;
import de.inmediasp.graphql.security.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserResolver implements GraphQLResolver<User> {
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    public String getToken(final User user) {
        return userService.getToken(user);
    }
}
