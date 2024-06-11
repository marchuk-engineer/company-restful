package smida.techtask.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

import java.security.Key;
import java.util.Date;

import static smida.techtask.utils.CookieUtils.REFRESH_TOKEN_COOKIE_NAME;

@UtilityClass
@Log4j2
@Getter
public class JwtUtils {

    public static final String BEARER = "Bearer ";
    public static Integer ACCESSTOKENEXPIRATIONMS;
    public static Integer REFRESHTOKENEXPIRATIONMS;
    private static String STATIC_SECRET;

    @Value("${jwtSecret}")
    public static void setStaticSecret(String staticSecret) {
        STATIC_SECRET = staticSecret;
    }

    @Value("${accessTokenExpirationMs}")
    public static void setAccessTokenExpirationMs(int accessTokenExpirationMs) {
        ACCESSTOKENEXPIRATIONMS = accessTokenExpirationMs;
    }

    @Value("${refreshTokenExpirationMs}")
    public static void setRefreshTokenExpirationMs(int refreshTokenExpirationMs) {
        REFRESHTOKENEXPIRATIONMS = refreshTokenExpirationMs;
    }

    public static String extractAccessToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isNotEmpty(token) && token.startsWith(BEARER)) {
            return token.substring(7);
        }
        return null;
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken);
    }

    public static boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken);
    }

    public static String extractUsername(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(STATIC_SECRET).build()
                    .parseClaimsJws(token)
                    .getBody();


        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Error parsing JWT token: {}", e.getMessage());
            return null;
        }
        return claims.getSubject();
    }

    public static String generateAccessToken(String username) {
        return generateToken(username, ACCESSTOKENEXPIRATIONMS);
    }

    public static String generateRefreshToken(String username) {
        return generateToken(username, REFRESHTOKENEXPIRATIONMS);
    }

    private static String generateToken(String username, int expirationMs) {
        Date now = new Date();
        long expirationMillis = expirationMs * 60L * 1000L;
        Date expiryDate = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key())
                .compact();
    }

    private static Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(STATIC_SECRET));
    }

    private static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(key()).build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
