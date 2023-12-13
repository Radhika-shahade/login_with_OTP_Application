package mailauthenticatorapp.util;

import com.fasterxml.jackson.databind.ext.Java7Handlers;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    public  void sendOtpOnEmail(String email,String otp) throws MessagingException {
//        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
//        simpleMailMessage.setTo(email);
//        simpleMailMessage.setSubject("verify OTP");
//        simpleMailMessage.setText("Hello, your OTP is "+ otp);
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        System.out.println(mimeMessage);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("verify OTP");
        mimeMessageHelper.setText("""
                <div>
                <a href="http://localhost:8082/verify-account?email=%s&otp=%s> target="_blank"> click link to verify</a>
                </div>
                """.formatted(email,otp), true);
        javaMailSender.send(mimeMessage);

    }

}
