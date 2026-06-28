package tgb.cryptoexchange.supportusers.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tgb.cryptoexchange.supportusers.dto.AuthRequest;
import tgb.cryptoexchange.supportusers.dto.AuthResponse;
import tgb.cryptoexchange.supportusers.dto.TokenPair;
import tgb.cryptoexchange.supportusers.dto.UserDTO;
import tgb.cryptoexchange.supportusers.exceptions.UnauthorizedException;
import tgb.cryptoexchange.supportusers.exceptions.UserAlreadyExistsException;
import tgb.cryptoexchange.supportusers.service.AuthenticationManagerService;
import tgb.cryptoexchange.supportusers.service.CookieService;
import tgb.cryptoexchange.supportusers.service.UserService;

@RestController
@Slf4j
@RequestMapping("/support-users")
public class SupportUsersController {

    private final CookieService cookieService;

    private final UserService userService;

    private final AuthenticationManagerService authService;

    public SupportUsersController(UserService userService, AuthenticationManagerService authService,
                                  CookieService cookieService) {
        this.userService = userService;
        this.authService = authService;
        this.cookieService = cookieService;
    }

    /**
     * Создает нового пользователя.
     *
     * @return {@link ResponseEntity} со статусом 200 (OK).
     * @throws UserAlreadyExistsException, PasswordValidationException со статусом 400 (Bad Request).
     */
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody final UserDTO request) {
        userService.create(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Аутентификация пользователя и выдача токенов доступа.
     *
     * @param authRequest данные для входа (логин/пароль или refresh токен)
     * @param response    HTTP-ответ для записи refresh токена в Cookie
     * @return {@link ResponseEntity} с accessToken в теле ответа
     * @throws UnauthorizedException если не переданы учетные данные
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        if (authRequest == null || authRequest.username() == null || (authRequest.password() == null
                && authRequest.refreshToken() == null)) {
            throw new UnauthorizedException("No credentials provided");
        }
        TokenPair tokens = authService.authenticate(authRequest);

        response.addHeader(HttpHeaders.SET_COOKIE,
                cookieService.createRefreshTokenCookie(tokens.refreshToken()).toString());
        return ResponseEntity.ok(new AuthResponse(tokens.accessToken()));
    }

}
