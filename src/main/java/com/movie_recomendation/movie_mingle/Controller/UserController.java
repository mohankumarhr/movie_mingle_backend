package com.movie_recomendation.movie_mingle.Controller;

import com.movie_recomendation.movie_mingle.Model.Movies;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.ResponseEntity.LoginResponse;
import com.movie_recomendation.movie_mingle.ResponseEntity.MovieResponse;
import com.movie_recomendation.movie_mingle.ResponseEntity.UserResponse;
import com.movie_recomendation.movie_mingle.Service.JwtService;
import com.movie_recomendation.movie_mingle.Service.MovieService;
import com.movie_recomendation.movie_mingle.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MovieService movieService;


    @RequestMapping("register")
    public Users RegisterUser(@RequestBody Users user) {
        return userService.RegisterUser(user);
    }

    @RequestMapping("/login")
    public Map<String, LoginResponse> login(@RequestBody Users user) {
        LoginResponse result = userService.login(user);
        return Collections.singletonMap("response", result);
    }

    @GetMapping("/getuser")
    public UserResponse getUsername(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        return userService.getUser(token);
    }

    @PostMapping("/updateuser")
    public String updateUser(@RequestBody UserResponse user) {
        return userService.updateUser(user);
    }

   @GetMapping("/resendverification")
   public String ResendVerificationMail(@RequestParam String username) {
        return userService.ResendVerificationMail(username);
   }

   @GetMapping("/forgotpassword")
   public String ForgotPassword(@RequestParam String username) {
        return userService.ForgotPassword(username);
   }

   @PostMapping("/addmovie")
   public String addMovie(@RequestParam String username, @RequestBody Movies movie) {
       System.out.println("Controller"+movie);
        return movieService.addMovie(username, movie);
   }

   @PostMapping("/likemovie")
   public String LikeMovie(@RequestParam int id, @RequestParam String username) {
        return movieService.likeMovie(id, username);
   }

   @PostMapping("/dislikemovie")
    public String disLikeMovie(@RequestParam int id, @RequestParam String username) {
        return movieService.dislikeMovie(id, username);
    }

   @DeleteMapping("/delete")
   public  String deleteMovie(@RequestParam int id) {
        return movieService.deleteMovie(id);
   }

   @GetMapping("/allmovies")
   public List<MovieResponse> getAllMovies() {
        return movieService.getMovies();
   }

   @GetMapping("/moviebyuser")
    public List<Movies> getMovieByUser(@RequestParam String username) {
        return movieService.getMoviesByUser(username);
   }

}
