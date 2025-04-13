package com.movie_recomendation.movie_mingle.Service;

import com.movie_recomendation.movie_mingle.Model.Movies;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Repo.MovieRepo;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import com.movie_recomendation.movie_mingle.ResponseEntity.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MovieService {

    @Autowired
    MovieRepo movieRepo;

    @Autowired
    UserRepo userRepo;

    public List<MovieResponse> getMovies() {
        List<Movies> movies = movieRepo.findAll();
        List<MovieResponse> movieResponses = new ArrayList<>();
        for (Movies movie : movies) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setId(movie.getId());
            movieResponse.setTmdb(movie.getTmdb_id());
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setUrl(movie.getUrl());
            movieResponse.setUsername(movie.getUser().getUsername());
            movieResponse.setLiked_users(movie.getLiked_users());
            movieResponses.add(movieResponse);
        }
        return movieResponses;
    }

    public Movies getMovieById(int id) {
        return movieRepo.findById(id).orElse(null);
    }

    public String addMovie(String username, Movies movie) {
        Users user = userRepo.findByUsername(username);
        movie.setUser(user);
        System.out.println("service"+movie);
        movieRepo.save(movie);
        return "Movie Added Successfully";
    }

    public String deleteMovie(int id) {
        movieRepo.deleteById(id);
        return "Movie Deleted Successfully";
    }

    public List<Movies> getMoviesByUser(String username) {
        Users user = userRepo.findByUsername(username);
        return movieRepo.findByUser(user);
    }

    public String likeMovie(int id, String username) {
        Movies movie = getMovieById(id);
        Set<String> linked = movie.getLiked_users();
        if (linked.contains(username)) {
            return "Liked already Movie Successfully";
        }
        linked.add(username);
        movie.setLiked_users(linked);
        movieRepo.save(movie);
        return "Movie Liked Successfully";
    }

    public String dislikeMovie(int id, String username) {
        Movies movie = getMovieById(id);
        Set<String> linked = movie.getLiked_users();
        if (linked.contains(username)) {
            linked.remove(username);
            movie.setLiked_users(linked);
            movieRepo.save(movie);
            return "disliked Movie Successfully";
        }

        return "Movie No liked";
    }
}
