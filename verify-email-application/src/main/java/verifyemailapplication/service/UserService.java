package verifyemailapplication.service;

import verifyemailapplication.entity.AppUser;

public interface UserService {
   AppUser authenticateUser(String username, String password);
    void generateOtp(AppUser user);
    boolean verifyOtp(AppUser user, String otp);
}
