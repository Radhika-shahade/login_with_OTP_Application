package verifyemailapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import verifyemailapplication.entity.AppUser;
import verifyemailapplication.repository.UserRepository;
import verifyemailapplication.util.OtpUtil;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    OtpUtil otpUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public AppUser authenticateUser(String username, String password) {

        Optional<AppUser> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (passwordEncoder().matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void generateOtp(AppUser user) {
        String otp = otpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpirationTime(LocalDateTime.now().plusMinutes(2));
        userRepository.save(user);
        // Send OTP to the user's email
        sendOtpEmail(user.getEmail(), otp);
    }

    @Override
    public boolean verifyOtp(AppUser user, String otp) {
        if (user.getOtp().equals(otp) && LocalDateTime.now().isBefore(user.getOtpExpirationTime())) {
            user.setOtp(null);
            user.setOtpExpirationTime(null);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP for Login");
        message.setText("Your OTP for login is: " + otp);
        javaMailSender.send(message);
    }
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
