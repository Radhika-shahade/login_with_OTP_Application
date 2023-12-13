package mailauthenticatorapp.controller;

import mailauthenticatorapp.dto.LoginDTO;
import mailauthenticatorapp.dto.RegisterDTO;
import mailauthenticatorapp.dto.UserResponse;
import mailauthenticatorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterDTO registerDto) {
        return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
    }

    @PutMapping("/user/otp/verify")
    public ResponseEntity<UserResponse> verify(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PutMapping("/user/otp/generate")
    public ResponseEntity<UserResponse> regenerateOTP(@RequestParam String email) {
        return new ResponseEntity<>(userService.generateOTP(email), HttpStatus.OK);
    }

    @PutMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }


}
