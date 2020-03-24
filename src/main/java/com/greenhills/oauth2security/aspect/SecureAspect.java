package com.greenhills.oauth2security.aspect;

import com.greenhills.oauth2security.model.security.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;


@Aspect
@Component
class SecureAspect {

    @Autowired
    EntityManager entityManager;

    private void applyDefaultAuthorizeFilter(){
        getCurrentUserId().ifPresent(id -> {
            Filter filter = entityManager.unwrap(Session.class).enableFilter("authorize");
            filter.setParameter("userId", id);

            Filter departmentFilter = entityManager.unwrap(Session.class).enableFilter("authorizeDepartment");
            departmentFilter.setParameter("userId", id);
        });

    }

    private void removeAuthorizeFilter(){
        getCurrentUserId().ifPresent(id -> {
            entityManager.unwrap(Session.class).disableFilter("authorize");
            entityManager.unwrap(Session.class).disableFilter("authorizeDepartment");
        });

    }

    private Optional<Long> getCurrentUserId() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .map(User::getId);
    }


    @Around("@annotation(com.greenhills.oauth2security.annotations.SecureRead)")
    public Object secureRead(ProceedingJoinPoint joinPoint) throws Throwable {
        applyDefaultAuthorizeFilter();

        Object proceed = joinPoint.proceed();

        removeAuthorizeFilter();

        return proceed;
    }
}
