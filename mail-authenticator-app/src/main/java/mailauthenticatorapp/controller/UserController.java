package mailauthenticatorapp.controller;

import mailauthenticatorapp.dto.LoginDTO;
import mailauthenticatorapp.dto.SignupDTO;
import mailauthenticatorapp.dto.UserResponse;
import mailauthenticatorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignupDTO signupDTO) {
        return new ResponseEntity<>(userService.signup(signupDTO), HttpStatus.OK);
    }

    @PutMapping("/otp/verify")
    public ResponseEntity<UserResponse> verify(@RequestParam String email, @RequestParam String otp) {
        return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PutMapping("/otp/generate")
    public ResponseEntity<UserResponse> regenerateOTP(@RequestParam String email) {
        return new ResponseEntity<>(userService.generateOTP(email), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginDTO loginDTO) {
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }


}
