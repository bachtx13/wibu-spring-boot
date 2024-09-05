package com.bachtx.mangaservice.interceptors;

import com.bachtx.mangaservice.contexts.AuthenticationContextHolder;
import com.bachtx.mangaservice.contexts.models.AuthenticationContext;
import com.bachtx.mangaservice.entities.User;
import com.bachtx.mangaservice.repositories.IUserRepository;
import com.bachtx.wibucommon.constant.SecurityConstant;
import com.bachtx.wibucommon.exceptions.AccessDeniedException;
import com.bachtx.wibucommon.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;
    private final IUserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try{
            String token = request.getHeader(SecurityConstant.AUTHORIZATION);
            String email = jwtUtil.getSubjectFromToken(token);
            User user = userRepository.findByEmail(email);
            if(user == null){
                throw new AccessDeniedException("User was not logged in!");
            }
            AuthenticationContext userContext = AuthenticationContext.builder()
                    .principal(user)
                    .authorities(user.getRoles())
                    .authenticated(true)
                    .build();
            AuthenticationContextHolder.setContext(userContext);
        } catch (Exception e){
            log.warn(e.getMessage());
            AuthenticationContextHolder.createEmptyContext();
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
