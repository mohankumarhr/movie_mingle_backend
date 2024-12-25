package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepo extends JpaRepository<Community, Integer> {
    Community findCommunityById(int id);
}
