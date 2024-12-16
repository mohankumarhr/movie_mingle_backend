package com.movie_recomendation.movie_mingle.Controller;


import com.movie_recomendation.movie_mingle.Model.OtpEntity;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Model.VerificationToken;
import com.movie_recomendation.movie_mingle.Repo.OtpRepo;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import com.movie_recomendation.movie_mingle.Repo.VerificationTokenRepo;
import com.movie_recomendation.movie_mingle.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
public class VerificationController {

    @Autowired
    VerificationTokenRepo verificationTokenRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    OtpRepo otpRepo;

    @Autowired
    EmailService emailService;

    @GetMapping("/verify")
    public ResponseEntity<Object> verifyAccount(@RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);

        if (verificationToken == null) {
            return new ResponseEntity<>("Invalid token", HttpStatus.NOT_ACCEPTABLE);
        }

        if (verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("Token expired", HttpStatus.NOT_ACCEPTABLE);
        }

        Users user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepo.save(user);
        return new ResponseEntity<>("Account verified successfully", HttpStatus.OK);
    }


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/verifyotp")
    public ResponseEntity<Object> verifyOtp(@RequestParam("otp") String otp,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password
                                ) {
        OtpEntity optEntity = otpRepo.findByOtp(otp);
        if (optEntity == null || !optEntity.getUser().getUsername().equals(username)) {
            return new ResponseEntity<>("Invalid otp", HttpStatus.NOT_ACCEPTABLE);
        }
        if (optEntity.getExpirationTime().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>("otp expired", HttpStatus.NOT_ACCEPTABLE);
        }
        Users user = optEntity.getUser();
        user.setPassword(encoder.encode(password));
        userRepo.save(user);
        emailService.sendEmail(user.getEmail(), "Password Changed", "Your password has been changed");
        return new ResponseEntity<>("Otp verified successfully and password changed", HttpStatus.OK);
    }


}
