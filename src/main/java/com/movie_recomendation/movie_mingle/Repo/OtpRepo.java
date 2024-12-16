package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.OtpEntity;
import com.movie_recomendation.movie_mingle.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<OtpEntity, Integer> {
    OtpEntity findByOtp(String otp);

    boolean existsByUser(Users user);

    OtpEntity findByUser(Users user);
}
