package com.project.vue.common.auth;
/*
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


     * 인증 처리 URL, 매니저 설정
     * @param url URL 경로

    public WebAuthenticationFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url, "POST"), authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	log.debug("## WebAuthenticationFilter");

        // 커스텀 토큰 생성
        WebAuthenticationToken authenticationToken = 
        		new WebAuthenticationToken(request.getParameter("userId"), request.getParameter("userPwd"));

        // 인증 매니저에 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
*/ 

// 2023-08-21 사용하지 않음.