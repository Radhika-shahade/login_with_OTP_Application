package mailauthenticatorapp.controller;

import mailauthenticatorapp.dto.LoginDTO;
import mailauthenticatorapp.dto.RegisterDto;
import mailauthenticatorapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity <String> register(@RequestBody RegisterDto registerDto){
return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
    }
    @PutMapping("/verify-account")
    public ResponseEntity <String> verify(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(userService.verifyAccount(email,otp), HttpStatus.OK);
    }
    @PutMapping("/regenerate-otp")
    public ResponseEntity <String> regenerateOTP(@RequestParam String email){
        return new ResponseEntity<>(userService.regenerateOTP(email), HttpStatus.OK);
    }
    @PutMapping("/login")
    public ResponseEntity <String> login(@RequestBody LoginDTO loginDTO){
        return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
    }


}
