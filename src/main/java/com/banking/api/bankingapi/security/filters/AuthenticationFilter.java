package com.banking.api.bankingapi.security.filters;

import com.banking.api.bankingapi.security.jwt.JwtServices;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jose4j.jwt.JwtClaims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtServices jwtService;
    private static final String AUTH_PREFIX = "Bearer ";

    @SneakyThrows
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            if (!authorization.startsWith(AUTH_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String token = authorization.substring(AUTH_PREFIX.length());
            JwtClaims jwtClaims = jwtService.validateJwtToken(token);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(jwtClaims.getSubject(), token
                            , Collections.singleton(new SimpleGrantedAuthority((String) jwtClaims.getClaimValue("role"))));
            authenticationToken.setDetails(jwtClaims);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
            return;

        }
        filterChain.doFilter(request, response);
    }
}
