package com.panonit.evently.config;

import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private static final String REALM_ACCESS_KEY = "realm_access";
    private static final String ROLES_KEY = "roles";
    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(source, extractAuthorities(source));
    }


    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // claims -> realm_access -> roles -> ROLE_ORGANIZER | ROLE_ATTENDEE | ROLE_STAFF
        Map<String, Object> realmAccess = jwt.getClaimAsMap(REALM_ACCESS_KEY);
        if (realmAccess == null || !realmAccess.containsKey(ROLES_KEY)) {
            return Collections.emptyList();
        }

        try {
            List<String> roles = (List<String>) realmAccess.get("roles");

            return roles.stream()
                    .filter(role -> role.startsWith(ROLE_PREFIX))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (ClassCastException e) {
            return Collections.emptyList();
        }
    }
}
