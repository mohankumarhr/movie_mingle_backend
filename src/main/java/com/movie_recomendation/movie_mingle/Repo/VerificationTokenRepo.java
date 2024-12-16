package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
        VerificationToken findByToken(String token);
        boolean existsByUser(Users user);
        VerificationToken findByUser(Users user);
}
