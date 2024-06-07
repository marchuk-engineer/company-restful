package smida.techtask.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smida.techtask.entities.User;
import smida.techtask.utils.CookieUtils;

import java.io.IOException;
import java.util.Objects;


/**
 * This filter intercepts incoming requests and processes JWT authentication.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String LOGOUT_ENDPOINT = "/logout";

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private UserDetails userDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // If the request is for the logout endpoint, proceed with the filter chain
        if (request.getRequestURI().equals(LOGOUT_ENDPOINT)) {
            chain.doFilter(request, response);
            return;
        }

        // Extract access token and refresh token from the request
        String accessToken = JwtService.extractAccessToken(request);
        String refreshToken = JwtService.extractRefreshToken(request);

        // Check if any token has been provided for authenticated requests
        if (accessToken == null || refreshToken == null) {
            log.warn("JWT token/tokens hasn't been provided");
        }
        // Check if refresh token is invalid and username is extracted from token
        else if (Objects.isNull(jwtService.extractUsername(refreshToken))) {
            log.warn("Invalid JWT refresh token has been provided");
            // Redirect to the logout endpoint
            response.sendRedirect(LOGOUT_ENDPOINT);
            return;
        }
        // Check if user is present by provided username
        else if (Objects.isNull(getUserDetails(jwtService.extractUsername(refreshToken)))) {
            log.warn("No user is present");
        } else {
            User user = (User) userDetails;
            String username = user.getUsername();

            // Validate access token
            if (jwtService.validateAccessToken(accessToken)) {
                handleValidAccessToken(response, refreshToken, userDetails, username);
            } else {
                try {
                    // Validate refresh token
                    jwtService.validateRefreshToken(refreshToken);
                    handleValidRefreshToken(response, userDetails, username);
                } catch (ExpiredJwtException e) {
                    // If refresh token is expired, redirect to the logout endpoint
                    response.sendRedirect(LOGOUT_ENDPOINT);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Refresh token is valid, so it takes just to generate new access token and update alive time for refresh token
     *
     * @param response     The HttpServletResponse object to set cookies and headers.
     * @param refreshToken The living refresh token.
     * @param userDetails  The UserDetails object associated with the user.
     * @param username        The user's username.
     */
    private void handleValidAccessToken(HttpServletResponse response, String refreshToken, UserDetails userDetails, String username) {
        String newAccessToken = jwtService.generateAccessToken(username);
        setAuthenticationResponseData(response, newAccessToken, refreshToken, userDetails);
    }

    /**
     * Generate new access and refresh tokens when the access token is no longer valid.
     *
     * @param response    The HttpServletResponse object to set cookies and headers.
     * @param userDetails The UserDetails object associated with the user.
     * @param username       The user's username.
     */
    private void handleValidRefreshToken(HttpServletResponse response, UserDetails userDetails, String username) {
        String newAccessToken = jwtService.generateAccessToken(username);
        String newRefreshToken = jwtService.generateRefreshToken(username);
        setAuthenticationResponseData(response, newAccessToken, newRefreshToken, userDetails);
    }

    /**
     * Extract UserDetails and if such is present, then set it on SecurityContextHolder.
     *
     * @param username The username obtained from the token
     * @return UserDetails from the database, or null if not found
     */
    private UserDetails getUserDetails(String username) {
        try {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            setSecurityContext(user);
            this.userDetails = user;
            return user;
        } catch (UsernameNotFoundException e) {
            return null;
        }
    }

    private void setAuthenticationResponseData(HttpServletResponse response, String accessToken, String refreshToken, UserDetails userDetails) {
        CookieUtils.addCookie(response, CookieUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken, jwtService.getRefreshTokenExpirationMs(), true, true);
        CookieUtils.addCookie(response, CookieUtils.USER_ID_COOKIE_NAME, ((User) userDetails).getId().toString(), jwtService.getRefreshTokenExpirationMs(), false, true);
        response.setHeader(HttpHeaders.AUTHORIZATION, JwtService.BEARER + accessToken);
    }

    private void setSecurityContext(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }

}
