package com.movie_recomendation.movie_mingle.Repo;

import com.movie_recomendation.movie_mingle.Model.Community;
import com.movie_recomendation.movie_mingle.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface CommunityRepo extends JpaRepository<Community, Integer> {
    Community findCommunityById(int id);

    List<Community> findCommunitiesByOwner(Users owner);

    @Query(value = "SELECT c.* FROM community c " +
            "JOIN community_members cm ON c.id = cm.member_communities_id " +
            "WHERE cm.members_id = :userId",
            nativeQuery = true)
    List<Community> findCommunitiesByUser(@Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM community_members WHERE member_communities_id = :cid AND members_id = :uid",
            nativeQuery = true)
    void removeMemberFromCommunity(@Param("cid") int cid, @Param("uid") int uid);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM community_members WHERE member_communities_id = :cid", nativeQuery = true)
    void removeAllMembersFromCommunity(@Param("cid") int cid);


}
