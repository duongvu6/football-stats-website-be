//package vn.ptit.project.epl_web.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vn.ptit.project.epl_web.domain.User;
//import vn.ptit.project.epl_web.dto.request.auth.RequestRegisterUserDTO;
//import vn.ptit.project.epl_web.dto.response.user.ResponseCreateUserDTO;
//import vn.ptit.project.epl_web.service.UserService;
//import vn.ptit.project.epl_web.util.exception.InvalidRequestException;
//
//@RestController
//@RequestMapping("/api/v1/auth")
//public class AuthController {
//    private UserService userService;
//    private PasswordEncoder passwordEncoder;
//
//    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//
//    @PostMapping("/register")
//    public ResponseEntity<ResponseCreateUserDTO> register(@Valid @RequestBody RequestRegisterUserDTO requestRegisterUserDTO) throws InvalidRequestException {
//        // System.out.println(requestRegisterUserDTO);
//        if (this.userService.isEmailExists(requestRegisterUserDTO.getEmail())) {
//            throw new InvalidRequestException("Email " + requestRegisterUserDTO.getEmail() + " is already exists. Please choose another email");
//        }
//        String hashedPassword = this.passwordEncoder.encode(requestRegisterUserDTO.getPassword());
//        requestRegisterUserDTO.setPassword(hashedPassword);
//        User createdUser = this.userService.handleSaveUser(this.userService.convertRequestRegisterUserDTOtoUser(requestRegisterUserDTO));
//        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertUserToResponseCreateUserDTO(createdUser));
//
//    }
//
//}
