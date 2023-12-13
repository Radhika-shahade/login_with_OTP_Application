package mailauthenticatorapp.service;

import jakarta.mail.MessagingException;
import mailauthenticatorapp.dto.LoginDTO;
import mailauthenticatorapp.dto.RegisterDTO;
import mailauthenticatorapp.dto.UserResponse;
import mailauthenticatorapp.entity.User;
import mailauthenticatorapp.repository.UserRepository;
import mailauthenticatorapp.util.EmailUtil;
import mailauthenticatorapp.util.GenerateOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {
    private final int MINUTE = 60 * 1000;
    @Autowired
    private GenerateOtp generateOtp;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    UserRepository userRepository;

    public UserResponse register(RegisterDTO registerDto) {
        try {

            User user = new User();
            user.setName(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(registerDto.getPassword());
            setOtp(user);
            emailUtil.sendOtpOnEmail(registerDto.getEmail(), user.getOtp());
            userRepository.save(user);
            return UserResponse.builder().message("user registration is successful").build();
        } catch (Exception e) {
            return UserResponse.builder().message("Oops! something went wrong, please try again!").build();
        }
    }

    public UserResponse verifyAccount(String email, String otp) {
        try {
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
            if (isUserValid(user, otp)) {
                user.setActive(true);
                userRepository.save(user);
            }
            return UserResponse.builder().message("OTP Verified and Login Successfully").build();
        } catch (Exception e) {
            return UserResponse.builder().message("OTP Expired! Please generate OTP again!").build();

        }
    }

    private boolean isUserValid(User user, String otp) {
        return user.getOtp().equals(otp) && checkValidity(user.getOtpGeneratedTime());
    }
    private boolean isValidPassword(User user, LoginDTO loginDTO) {
        return loginDTO.getPassword().equals(user.getPassword());
    }
    private boolean isUserVerified(User user)
    {
        return user.isActive();
    }

    private boolean checkValidity(LocalDateTime otpGeneratedTime) {
        return Duration.between(otpGeneratedTime, LocalDateTime.now()).getSeconds() < (2 * MINUTE);
    }

    public UserResponse generateOTP(String email) {
        try {
            User user = userRepository.findByEmail(email).
                    orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
            setOtp(user);
            emailUtil.sendOtpOnEmail(email, user.getOtp());
            userRepository.save(user);
            return UserResponse.builder().message("OTP Verification Link has been sent to given email id!").build();
        } catch (Exception e) {
            return UserResponse.builder().message("Oops! something went wrong!").build();
        }
    }

    private void setOtp(User user) {
        String otp = generateOtp.generateOtp();
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
    }

    public UserResponse login(LoginDTO loginDTO) {
        try {
            User user = userRepository.findByEmail(loginDTO.getEmail()).
                    orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDTO.getEmail()));

//        if(loginDTO.isLoginWithOTP()){
//            generateOTP(loginDTO.getEmail());
            if (!isValidPassword(user, loginDTO)) {
                return UserResponse.builder().message("password is incorrect").build();
            } else if (!isUserVerified(user)) {
                return UserResponse.builder().message("your account is not verified").build();
            }
            return UserResponse.builder().message("Login Successfully").build();
        } catch (Exception e) {
            return UserResponse.builder().message("Oops! something went wrong!").build();
        }
    }
}
