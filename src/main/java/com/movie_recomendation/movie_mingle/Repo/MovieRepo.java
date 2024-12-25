package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.Movies;
import com.movie_recomendation.movie_mingle.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movies, Integer> {
    List<Movies> findByUser(Users user);
}
