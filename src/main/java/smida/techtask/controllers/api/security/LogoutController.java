package smida.techtask.controllers.api.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smida.techtask.constants.ApiConstants;
import smida.techtask.controllers.LogoutApi;
import smida.techtask.utils.CookieUtils;

/**
 * Controller handling user logout functionality.
 */
@RestController
@RequestMapping(ApiConstants.BASE_URL + "/auth")
@RequiredArgsConstructor

public class LogoutController implements LogoutApi {

    @GetMapping("/sign-out")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteAllCookies(request, response);
    }

}
