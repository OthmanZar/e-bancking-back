package atos.formation.gatewayservice.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {

        System.out.println("JWTTT IS HERE FOR CONVERTING ! ");
        Collection<GrantedAuthority> authorities = Stream.concat(
                extractJwtGrantedAuthorities(jwt).stream(),
                extractRealmRoles(jwt).stream()
        ).collect(Collectors.toSet());

        authorities.forEach(authority -> System.out.println("Authority: " + authority.getAuthority()));

        return Mono.just(new JwtAuthenticationToken(jwt, authorities));
    }

    private Collection<? extends GrantedAuthority> extractJwtGrantedAuthorities(Jwt jwt) {

        return new JwtGrantedAuthoritiesConverter().convert(jwt);
    }

    private Collection<? extends GrantedAuthority> extractRealmRoles(Jwt jwt) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess != null) {
            List<String> roles = (List<String>) realmAccess.get("roles");
            if (roles != null) {
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
            }
        }
        return authorities;
    }

}
