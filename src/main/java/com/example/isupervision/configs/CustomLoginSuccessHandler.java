package com.example.isupervision.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {
        String homepageURL = null;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authentication.getPrincipal();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_ASSISTANT")){
                homepageURL = "/staffHomepage";
                break;
            }else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")){
                homepageURL = "/studentHomepage";
                break;
            }
        }

        if (homepageURL == null) {
            throw new IllegalStateException();
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, homepageURL);
    }

}
