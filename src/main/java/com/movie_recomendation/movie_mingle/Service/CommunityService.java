package com.movie_recomendation.movie_mingle.Service;

import com.movie_recomendation.movie_mingle.Model.Community;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Repo.CommunityRepo;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommunityService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    CommunityRepo communityRepo;

    public String createCommunity(String communityName, String ownerName) {
        Users owner = userRepo.findByUsername(ownerName);
        Community community = new Community();
        community.setName(communityName);
        community.setOwner(owner);
        communityRepo.save(community);
        return "Community created successfully";
    }

    public String deleteCommunity(int communityId) {
        communityRepo.deleteById(communityId);
        return "Community deleted successfully";
    }

    public String updateCommunity(int communityId, String communityName) {
        communityRepo.findById(communityId).get().setName(communityName);
        return "Community Name updated successfully";
    }

    public String addMemberToCommunity(int communityId, String username) {
        Users user = userRepo.findByUsername(username);
        Community community = communityRepo.findCommunityById(communityId);
        if (community != null) {
            community.getMembers().add(user);
            communityRepo.save(community);
            System.out.println(community.toString());
            return "Member added successfully";
        }else {
            return "Community not found";
        }
    }

    public String removeMemberFromCommunity(int communityId, String username) {
        Users user = userRepo.findByUsername(username);
        System.out.println(user.toString());
        Community community = communityRepo.findById(communityId).orElse(null);
        if (community != null) {
            System.out.println(community.getName());
            System.out.println(community.getMembers());
            community.getMembers().remove(user);
            communityRepo.save(community);
            System.out.println(community.getMembers().toString());
            return "Member removed successfully";
        }else {
            return "Community not found";
        }
    }

    public Community getCommunity(int communityId) {

        Community community = communityRepo.findCommunityById(communityId);
        Set<Users> copy = community.getMembers();
        System.out.println(community);
        return community;
    }

}
