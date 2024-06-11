package smida.techtask.controllers.api.security;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.LoginApi;
import smida.techtask.dto.security.LoginDto;
import smida.techtask.entities.User;
import smida.techtask.security.JwtService;
import smida.techtask.services.LoginService;
import smida.techtask.utils.CookieUtils;

import static smida.techtask.security.JwtService.BEARER;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL + "/auth/")
public class LoginController implements LoginApi {

    private final LoginService loginService;
    private final JwtService jwtService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public void basicLogin(@RequestBody @Valid @NotNull LoginDto requestBody,
                           HttpServletResponse response) {
        User user = loginService.loginWithUsernameAndPassword(requestBody);
        setHeadersAndCookies(user, response);
    }

    private void setHeadersAndCookies(User user, HttpServletResponse response) {
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        String accessToken = jwtService.generateAccessToken(user.getUsername());

        CookieUtils.addCookie(response, CookieUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken, jwtService.getRefreshTokenExpirationMs(), true, true);
        CookieUtils.addCookie(response, CookieUtils.USER_ID_COOKIE_NAME, user.getId().toString(), jwtService.getRefreshTokenExpirationMs(), false, true);
        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
    }

}
