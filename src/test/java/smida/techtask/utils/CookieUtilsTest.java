package smida.techtask.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CookieUtilsTest {

    @Test
    void getCookie() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getCookies()).thenReturn(new Cookie[]{new Cookie("USER_ID", "123")});

        Optional<Cookie> cookie = CookieUtils.getCookie(request, "USER_ID");
        assertTrue(cookie.isPresent());
        assertEquals("123", cookie.get().getValue());

        cookie = CookieUtils.getCookie(request, "NON_EXISTING_COOKIE");
        assertFalse(cookie.isPresent());
    }

    @Test
    void addCookie() {
        HttpServletResponse response = mock(HttpServletResponse.class);

        CookieUtils.addCookie(response, "TEST_COOKIE", "test_value", 3600, true, true);
        verify(response, times(1)).addCookie(any());
    }

    @Test
    void deleteCookie() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie cookie = new Cookie("TEST_COOKIE", "test_value");

        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        CookieUtils.deleteCookie(request, response, "TEST_COOKIE");
        verify(response, times(1)).addCookie(any());
    }

    @Test
    void deleteAllCookies() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie[] cookies = {new Cookie("COOKIE1", "value1"), new Cookie("COOKIE2", "value2")};

        when(request.getCookies()).thenReturn(cookies);
        CookieUtils.deleteAllCookies(request, response);
        verify(response, times(cookies.length)).addCookie(any());
    }

}
