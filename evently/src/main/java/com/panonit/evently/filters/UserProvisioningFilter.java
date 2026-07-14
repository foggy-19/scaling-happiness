package com.panonit.evently.filters;

import com.panonit.evently.domain.entities.User;
import com.panonit.evently.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository repository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt jwt) {
            UUID keycloakId = UUID.fromString(Objects.requireNonNull(jwt.getSubject())); // throws exception
            String name = jwt.getClaimAsString("preferred_username");
            String email = jwt.getClaimAsString("email");

            if (!repository.existsById(keycloakId)) {
                User user = new User();
                user.setId(keycloakId);
                user.setName(name);
                user.setEmail(email);

                repository.save(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
