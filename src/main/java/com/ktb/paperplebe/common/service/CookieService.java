package com.ktb.paperplebe.common.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CookieService {
    @Value("${app.properties.frontCookieDomain}")
    private String frontCookieDomain;


    @Value("${app.properties.cookieSameSite}")
    private String cookieSameSite;

    // TO DO - secure, domain, sameSite 설정 추가
    public ResponseCookie createCookie(String cookieName, String cookieValue) {
        return ResponseCookie.from(cookieName, cookieValue)
                .path("/")
                .httpOnly(true)
                .domain(frontCookieDomain)
                .sameSite(cookieSameSite)
                .build();
    }

    public void deleteCookie(HttpServletResponse response, String cookieName) {
        ResponseCookie deleteCookie = ResponseCookie.from(cookieName, "")
                .path("/")
                .httpOnly(true)
                .maxAge(0)
                .domain(frontCookieDomain)
                .sameSite(cookieSameSite)
                .build();

        response.addHeader(SET_COOKIE, deleteCookie.toString());
    }
}
