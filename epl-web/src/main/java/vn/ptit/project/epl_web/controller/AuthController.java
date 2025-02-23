package vn.ptit.project.epl_web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.domain.User;
import vn.ptit.project.epl_web.dto.request.auth.RequestLoginDTO;
import vn.ptit.project.epl_web.dto.request.auth.RequestRegisterUserDTO;
import vn.ptit.project.epl_web.dto.response.auth.ResponseLoginDTO;
import vn.ptit.project.epl_web.dto.response.user.ResponseCreateUserDTO;
import vn.ptit.project.epl_web.service.UserService;
import vn.ptit.project.epl_web.util.SecurityUtil;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Controller", description = "APIs for handling authentication")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private SecurityUtil securityUtil;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseCreateUserDTO> register(@Valid @RequestBody RequestRegisterUserDTO requestRegisterUserDTO) throws InvalidRequestException {
        if (this.userService.isEmailExists(requestRegisterUserDTO.getEmail())) {
            throw new InvalidRequestException("Email " + requestRegisterUserDTO.getEmail() + " is already exists. Please choose another email");
        }
        String hashedPassword = this.passwordEncoder.encode(requestRegisterUserDTO.getPassword());
        requestRegisterUserDTO.setPassword(hashedPassword);
        User createdUser = this.userService.handleSaveUser(requestRegisterUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.toResponseCreateUserDTO(createdUser));
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@Valid @RequestBody RequestLoginDTO requestLoginDTO) {
        //take input(username and password) into security
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(requestLoginDTO.getUsername(), requestLoginDTO.getPassword());

        //authenticate user (override UserDetailsService bean)
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        //push information if success to securitycontext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //not pass password
        ResponseLoginDTO ResponseLoginDTO = new ResponseLoginDTO();
        User currentUser = this.userService.getUserByUsername(requestLoginDTO.getUsername());
        if (currentUser != null) {
            ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(
                    currentUser.getId(),
                    currentUser.getEmail(),
                    currentUser.getName(),
                    currentUser.getRole()
            );
            ResponseLoginDTO.setUser(userLogin);
        }
        String accessToken = this.securityUtil.createAccessToken(requestLoginDTO.getUsername(), ResponseLoginDTO);
        ResponseLoginDTO.setAccessToken(accessToken);

        String refreshToken = this.securityUtil.createRefreshToken(requestLoginDTO.getUsername(), ResponseLoginDTO);

        this.userService.updateUserToken(refreshToken, requestLoginDTO.getUsername());

        //set cookies
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                // .domain("example.com")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(ResponseLoginDTO);

    }
    @GetMapping("/account")
    @ApiMessage("Fetch account")
    public ResponseEntity<ResponseLoginDTO.UserGetAccount> getAccount() {

        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        // Lấy user
        User currentUserDB = this.userService.getUserByUsername(email);
        ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin();
        ResponseLoginDTO.UserGetAccount userGetAccount = new ResponseLoginDTO.UserGetAccount();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
             userLogin.setRole(currentUserDB.getRole());
            userGetAccount.setUser(userLogin);
        }

        return ResponseEntity.ok().body(userGetAccount);
    }
    @GetMapping("/refresh")
    @ApiMessage("Get User by refresh token")

    public ResponseEntity<ResponseLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refreshToken) throws InvalidRequestException {
        if (refreshToken.equals("abc")) {
            throw new InvalidRequestException("You don't have refresh token in cookies");
        }

        // check valid
        Jwt decodedToken = this.securityUtil.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();

        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refreshToken, email);
        if (currentUser == null) {
            throw new InvalidRequestException("Refresh Token not valid");
        }

        // issue new token/set refresh token as cookies
        ResponseLoginDTO res = new ResponseLoginDTO();
        User currentUserDB = this.userService.getUserByUsername(email);
        if (currentUserDB != null) {

            ResponseLoginDTO.UserLogin userLogin = new ResponseLoginDTO.UserLogin(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getName(),
                     currentUserDB.getRole()
            );
            res.setUser(userLogin);
        }

        // create access_token token
        String accessToken = this.securityUtil.createAccessToken(email, res);

        res.setAccessToken(accessToken);

        // create refresh token
        String newRefreshToken = this.securityUtil.createRefreshToken(email, res);

        // Update user with new_refresh_token
        this.userService.updateUserToken(newRefreshToken, email);

        // set cookies
        ResponseCookie resCookies = ResponseCookie.from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                // .domain("example.com")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    @PostMapping("/logout")
    @ApiMessage("Logout User")
    public ResponseEntity<Void> logout() throws InvalidRequestException {
        // Lấy email từ Spring security
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";

        if (email.equals("email")) {
            throw new InvalidRequestException("Access Token is not valid");
        }

        // Update refresh token = null
        this.userService.updateUserToken(null, email);

        // remove refresh token cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }
}