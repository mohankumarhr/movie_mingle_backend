package com.movie_recomendation.movie_mingle.Service;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Model.VerificationToken;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import com.movie_recomendation.movie_mingle.Repo.VerificationTokenRepo;
import com.movie_recomendation.movie_mingle.ResponseEntity.LoginResponse;
import com.movie_recomendation.movie_mingle.ResponseEntity.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtService jwtService;

    @Autowired
    VerificationTokenRepo verificationTokenRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    OtpService otpService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users RegisterUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        String token = UUID.randomUUID().toString();
        createVerificationToken(user, token);
        String url = "https://movieminglebackend-production.up.railway.app";
//      String url  = "http://localhost:8080";
        String verificationLink = url + "/verify?token=" + token;
        emailService.sendEmail(user.getEmail(), "Email Verification", "Click the link to verify your email: " + verificationLink);
        return user;
    }

    public LoginResponse login(Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        Users full_user_details = userRepo.findByUsername(user.getUsername());
        System.out.println(full_user_details.isEmailVerified());
        LoginResponse response = new LoginResponse();
        if (authentication.isAuthenticated() && full_user_details.isEmailVerified()) {
            response.setToken(jwtService.generateToken(user));
            response.setEmailVerified(full_user_details.isEmailVerified());
            response.setEmail(full_user_details.getEmail());
            return response;
        }else if (!full_user_details.isEmailVerified()) {
            response.setToken("failure");
            response.setEmailVerified(false);
            return response;
        }else {
            response.setToken("failure");
            response.setEmailVerified(false);
            return response;
        }
    }


    public UserResponse getUser(String token) {
        String username = jwtService.extractUserName(token);
        Users user = userRepo.findByUsername(username);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }

    public String updateUser(UserResponse user){
        System.out.println(user.toString());
        Users full_user_details = userRepo.findById(user.getId()).orElse(null);
        if (full_user_details != null){
            full_user_details.setUsername(user.getUsername());
            full_user_details.setEmail(user.getEmail());
            userRepo.save(full_user_details);
            return "success";
        }else {
            return "failure";
        }
    }

    public String ResendVerificationMail(String username){
        Users user = userRepo.findByUsername(username);

        if (user == null) {
            return "User with this email does not exist.";
        }

        if (user.isEmailVerified()) {
            return "User is already verified.";
        }

        // Check for an existing token
        VerificationToken verificationToken = verificationTokenRepo.findByUser(user);

        // Generate a new token if it doesn't exist or is expired
        if (verificationToken == null || verificationToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            if(verificationToken !=null) {
               verificationTokenRepo.delete(verificationToken);
            }
            String newToken = UUID.randomUUID().toString();
            verificationToken = new VerificationToken();
            verificationToken.setToken(newToken);
            verificationToken.setUser(user);
            verificationToken.setExpirationTime(LocalDateTime.now().plusMinutes(10));
            verificationTokenRepo.save(verificationToken);
        }

        // Send the verification email
        String verificationLink = "http://localhost:8080/verify?token=" + verificationToken.getToken();
        emailService.sendEmail(user.getEmail(), "Resend Email Verification", "Click the link to verify your email: " + verificationLink);

        return "Verification email resent successfully.";
    }


    public void createVerificationToken(Users user, String token) {
//        if(verificationTokenRepo.existsByUser(user)) {
//            VerificationToken verificationToken = verificationTokenRepo.findByUser(user);
//            verificationTokenRepo.delete(verificationToken);
//        }
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationTime(LocalDateTime.now().plusMinutes(10));
        verificationTokenRepo.save(verificationToken);
    }

    public String ForgotPassword(String username) {
        Users user = userRepo.findByUsername(username);
        if (user == null) {
            return "User with this username does not exist.";
        }
        return otpService.generateOtp(user);
    }
}

