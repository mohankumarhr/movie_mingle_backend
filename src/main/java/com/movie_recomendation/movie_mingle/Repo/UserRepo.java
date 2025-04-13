package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    @Query(value = "SELECT u.* FROM users u " +
            "JOIN community_members cm ON u.id = cm.members_id " +
            "WHERE cm.member_communities_id = :communityId",
            nativeQuery = true)
    List<Users> findMembersByCommunity(@Param("communityId") int communityId);



}
