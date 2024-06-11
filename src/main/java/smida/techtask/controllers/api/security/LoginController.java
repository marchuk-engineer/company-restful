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
import smida.techtask.services.LoginService;
import smida.techtask.utils.CookieUtils;
import smida.techtask.utils.JwtUtils;

import static smida.techtask.utils.JwtUtils.BEARER;


@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.BASE_URL + "/auth/")
public class LoginController implements LoginApi {

    private final LoginService loginService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public void basicLogin(@RequestBody @Valid @NotNull LoginDto requestBody,
                           HttpServletResponse response) {
        User user = loginService.loginWithUsernameAndPassword(requestBody);
        setHeadersAndCookies(user, response);
    }

    private void setHeadersAndCookies(User user, HttpServletResponse response) {
        String refreshToken = JwtUtils.generateRefreshToken(user.getUsername());
        String accessToken = JwtUtils.generateAccessToken(user.getUsername());

        CookieUtils.addCookie(response, CookieUtils.REFRESH_TOKEN_COOKIE_NAME, refreshToken, JwtUtils.REFRESHTOKENEXPIRATIONMS, true, true);
        CookieUtils.addCookie(response, CookieUtils.USER_ID_COOKIE_NAME, user.getId().toString(), JwtUtils.ACCESSTOKENEXPIRATIONMS, false, true);
        response.setHeader(HttpHeaders.AUTHORIZATION, BEARER + accessToken);
    }

}
