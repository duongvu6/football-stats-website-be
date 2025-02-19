package vn.ptit.project.epl_web.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.ptit.project.epl_web.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



}
