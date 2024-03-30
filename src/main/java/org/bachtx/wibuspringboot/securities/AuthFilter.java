package org.bachtx.wibuspringboot.securities;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.bachtx.wibuspringboot.constants.SecurityConstant;
import org.bachtx.wibuspringboot.entities.UserPrincipal;
import org.bachtx.wibuspringboot.utils.TokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@AllArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeaderToken = request.getHeader(SecurityConstant.AUTHORIZATION);
            if (StringUtils.hasText(authHeaderToken) && tokenUtil.validateToken(authHeaderToken)) {
                String username = tokenUtil.getUsernameFromToken(authHeaderToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(username);
                    if (userPrincipal != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Set<String> skipUrls = new HashSet<>(List.of("/auth/**"));
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }
}
