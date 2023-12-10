package mailauthenticatorapp.service;

import jakarta.mail.MessagingException;
import mailauthenticatorapp.dto.LoginDTO;
import mailauthenticatorapp.dto.RegisterDto;
import mailauthenticatorapp.entity.AppUser;
import mailauthenticatorapp.repository.UserRepository;
import mailauthenticatorapp.util.EmailUtil;
import mailauthenticatorapp.util.GenerateOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {
    @Autowired
    private GenerateOtp generateOtp;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    UserRepository userRepository;

    public String register(RegisterDto registerDto) {
        String otp = generateOtp.generateOtp();
        try {
            emailUtil.sendOtpOnEmail(registerDto.getEmail(), otp);

        } catch (MessagingException e) {
            throw new RuntimeException("unable to send otp please try again");
        }
        AppUser user = new AppUser();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "user registration is successful";
    }

    public String verifyAccount(String email, String otp) {
        AppUser user = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        if (user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (2 * 60)) {
            user.setActive(true);
            userRepository.save(user);
            return "otp verified ou can login";
        }
        return "please regenerate otp ";
    }

    public String regenerateOTP(String email) {
        AppUser user = userRepository.findByEmail(email).
                orElseThrow(() -> new RuntimeException("User not found with this email: " + email));
        String otp = generateOtp.generateOtp();
        try {
            emailUtil.sendOtpOnEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("unable to send otp please try again ");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "email send....please verify account within 2 minute";
    }

    public String login(LoginDTO loginDTO) {
        AppUser user = userRepository.findByEmail(loginDTO.getEmail()).
                orElseThrow(() -> new RuntimeException("User not found with this email: " + loginDTO.getEmail()));
    if(!loginDTO.getPassword().equals(user.getPassword()))
        {
            return "password is incorrect";
        }
        else if(!user.isActive())
        {
            return "your account is not verified";
        }
        return "Login successfully";
    }
}
