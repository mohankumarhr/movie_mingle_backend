package com.movie_recomendation.movie_mingle.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.movie_recomendation.movie_mingle.Service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin
public class TMDBController {

    @Autowired
    TMDBService tmdbService;

    @GetMapping("/search")
    public JsonNode searchMovies(@RequestParam String query) {
        return tmdbService.searchMovie(query);
    }

}
