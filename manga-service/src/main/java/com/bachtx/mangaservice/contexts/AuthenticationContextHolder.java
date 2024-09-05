package com.bachtx.mangaservice.contexts;

import com.bachtx.mangaservice.contexts.models.AuthenticationContext;

public class AuthenticationContextHolder {
    private static final ThreadLocal<AuthenticationContext> contextHolder = new ThreadLocal<>();
    public static void setContext(AuthenticationContext context) {
        contextHolder.set(context);
    }

    public static AuthenticationContext getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }

    public static void createEmptyContext(){
        AuthenticationContext emptyContext = new AuthenticationContext();
        contextHolder.set(emptyContext);
    }
}
