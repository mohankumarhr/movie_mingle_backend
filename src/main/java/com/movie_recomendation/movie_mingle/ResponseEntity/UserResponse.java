package com.movie_recomendation.movie_mingle.ResponseEntity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserResponse {
    private int id;
    private String username;
    private String email;

}
