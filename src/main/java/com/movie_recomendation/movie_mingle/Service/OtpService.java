package com.movie_recomendation.movie_mingle.Service;

import com.movie_recomendation.movie_mingle.Model.OtpEntity;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Repo.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {

    @Autowired
    OtpRepo otpRepo;

    @Autowired
    EmailService emailService;

    private static final SecureRandom random = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public String generateOtp(Users user) {

        if(otpRepo.existsByUser(user)){
            OtpEntity otpEntity = otpRepo.findByUser(user);
            otpRepo.delete(otpEntity);
        }

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generate a random digit (0-9)
        }
        OtpEntity newOtp = new OtpEntity();
        newOtp.setOtp(otp.toString());
        newOtp.setUser(user);
        newOtp.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        otpRepo.save(newOtp);
        emailService.sendEmail(user.getEmail(), "OTP Generated", "Yur otp to change password" + otp.toString());
        return "otp generated Successfully";
    }

}
