package com.movie_recomendation.movie_mingle.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MovieResponse {

    private int tmdb;
    private String title;
    private String url;
    private String username;

}
