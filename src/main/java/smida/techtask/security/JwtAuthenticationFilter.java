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
import smida.techtask.utils.JwtUtils;

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

    private final UserDetailsService userDetailsService;

    private UserDetails userDetails;

    /**
     * Performs the authentication and authorization logic for each HTTP request.
     * If the request URI is for the logout endpoint, the filter chain proceeds without authentication.
     * Otherwise, it extracts the access token and refresh token from the request.
     * If either token is missing, a warning is logged.
     * If the refresh token is invalid or no user is associated with it, the user is redirected to the logout endpoint.
     * Otherwise, it validates the access token.
     * If the access token is valid, it continues processing the request.
     * If the access token is invalid, it tries to validate the refresh token.
     * If the refresh token is expired, the user is redirected to the logout endpoint.
     * If the refresh token is valid, new access and refresh tokens are generated and the response is updated accordingly.
     * Finally, the filter chain proceeds with the request.
     *
     * @param request  The {@link HttpServletRequest} object representing the incoming request.
     * @param response The {@link HttpServletResponse} object representing the outgoing response.
     * @param chain    The {@link FilterChain} object representing the filter chain.
     * @throws IOException      If an I/O error occurs during the handling of the request or response.
     * @throws ServletException If the request could not be handled due to a servlet-specific problem.
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getRequestURI().equals(LOGOUT_ENDPOINT)) {
            chain.doFilter(request, response);
            return;
        }

        String accessToken = JwtUtils.extractAccessToken(request);
        String refreshToken = JwtUtils.extractRefreshToken(request);

        if (accessToken == null || refreshToken == null) {
            log.warn("JWT token/tokens hasn't been provided");
        } else if (Objects.isNull(JwtUtils.extractUsername(refreshToken))) {
            log.warn("Invalid JWT refresh token has been provided");
            response.sendRedirect(LOGOUT_ENDPOINT);
            return;
        } else if (Objects.isNull(getUserDetails(JwtUtils.extractUsername(refreshToken)))) {
            log.warn("No user is present");
        } else {
            User user = (User) userDetails;
            String username = user.getUsername();

            if (JwtUtils.validateAccessToken(accessToken)) {
                handleValidAccessToken(response, refreshToken, userDetails, username);
            } else {
                try {
                    JwtUtils.validateRefreshToken(refreshToken);
                    handleValidRefreshToken(response, userDetails, username);
                } catch (ExpiredJwtException e) {
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
     * @param username     The user's username.
     */
    private void handleValidAccessToken(HttpServletResponse response, String refreshToken, UserDetails userDetails, String username) {
        String newAccessToken = JwtUtils.generateAccessToken(username);
        setAuthenticationResponseData(response, newAccessToken, refreshToken, userDetails);
    }

    /**
     * Generate new access and refresh tokens when the access token is no longer valid.
     *
     * @param response    The HttpServletResponse object to set cookies and headers.
     * @param userDetails The UserDetails object associated with the user.
     * @param username    The user's username.
     */
    private void handleValidRefreshToken(HttpServletResponse response, UserDetails userDetails, String username) {
        String newAccessToken = JwtUtils.generateAccessToken(username);
        String newRefreshToken = JwtUtils.generateRefreshToken(username);
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
        CookieUtils.addCookie(response, CookieUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken, JwtUtils.REFRESHTOKENEXPIRATIONMS, true, true);
        CookieUtils.addCookie(response, CookieUtils.USER_ID_COOKIE_NAME, ((User) userDetails).getId().toString(), JwtUtils.ACCESSTOKENEXPIRATIONMS, false, true);
        response.setHeader(HttpHeaders.AUTHORIZATION, JwtUtils.BEARER + accessToken);
    }

    private void setSecurityContext(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }

}
