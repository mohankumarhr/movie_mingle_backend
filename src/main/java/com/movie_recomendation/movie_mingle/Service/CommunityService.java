package com.movie_recomendation.movie_mingle.Service;

import com.movie_recomendation.movie_mingle.Model.Community;
import com.movie_recomendation.movie_mingle.Model.LikesEntry;
import com.movie_recomendation.movie_mingle.Model.Users;
import com.movie_recomendation.movie_mingle.Repo.CommunityRepo;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import com.movie_recomendation.movie_mingle.ResponseEntity.CommunityResponse;
import com.movie_recomendation.movie_mingle.ResponseEntity.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        communityRepo.removeAllMembersFromCommunity(communityId);
        communityRepo.deleteById(communityId);
        return "Community deleted successfully";
    }

    public String updateCommunity(int communityId, String communityName) {
        communityRepo.findById(communityId).get().setName(communityName);
        return "Community Name updated successfully";
    }

    public ResponseEntity<String> addMemberToCommunity(int communityId, String username) {
        Users user = userRepo.findByUsername(username);
        Community community = communityRepo.findCommunityById(communityId);

        if (community == null || user == null) {
            return new ResponseEntity<>("Community or User not found", HttpStatus.NOT_FOUND);
        }

        if(community.getOwner().equals(user)) {
            return new ResponseEntity<>("You are commmunity owner", HttpStatus.NOT_ACCEPTABLE);
        }

        Set<Users> members = community.getMembers();
        if (members == null) {
            members = new HashSet<>();
        }

        members.add(user);
        community.setMembers(members);
        communityRepo.save(community);

        return new ResponseEntity<>("Community added successfully", HttpStatus.CREATED);
    }

    public  String exitFromCommunity(int communityId, String username) {
            Users user = userRepo.findByUsername(username);
            communityRepo.removeMemberFromCommunity(communityId, user.getId());
            return "members removed successfully";
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

    public CommunityResponse getCommunity(int communityId) {

        Community community = communityRepo.findCommunityById(communityId);
        Set<Users> copy = community.getMembers();
        System.out.println(community);
        CommunityResponse communityResponse = new CommunityResponse();
        communityResponse.setId(community.getId());
        communityResponse.setName(community.getName());
        communityResponse.setMovies(community.getMovies());
        UserResponse userResponse = new UserResponse();
        userResponse.setId(community.getOwner().getId());
        userResponse.setUsername(community.getOwner().getUsername());
        userResponse.setEmail(community.getOwner().getEmail());

        communityResponse.setOwner(userResponse);

        return communityResponse;
    }


    public List<UserResponse> getCommunityMembers(int communityId) {
        List<UserResponse> community_members = new ArrayList<>();
        List<Users> members = userRepo.findMembersByCommunity(communityId);
        for (Users user : members) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setUsername(user.getUsername());
            community_members.add(userResponse);
        }
        return community_members;
    }


    public List<CommunityResponse> getMemberedCommunity(int id){
        List<Community> communities = communityRepo.findCommunitiesByUser(id);
        List<CommunityResponse> community_members = new ArrayList<>();
        for (Community community : communities) {

            CommunityResponse communityResponse = new CommunityResponse();
            communityResponse.setId(community.getId());
            communityResponse.setName(community.getName());
            communityResponse.setMovies(community.getMovies());

            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(community.getOwner().getUsername());
            userResponse.setEmail(community.getOwner().getEmail());
            userResponse.setId(community.getOwner().getId());

            communityResponse.setOwner(userResponse);

            community_members.add(communityResponse);
        }
        return community_members;
    }

    public List<CommunityResponse> getOwnedCommunity(String name){
        Users users = userRepo.findByUsername(name);
        List<Community> communities = communityRepo.findCommunitiesByOwner(users);

        List<CommunityResponse> community_members = new ArrayList<>();
        for (Community community : communities) {
            CommunityResponse communityResponse = new CommunityResponse();
            communityResponse.setId(community.getId());
            communityResponse.setName(community.getName());
            communityResponse.setMovies(community.getMovies());

            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(community.getOwner().getUsername());
            userResponse.setEmail(community.getOwner().getEmail());
            userResponse.setId(community.getOwner().getId());

            communityResponse.setOwner(userResponse);
            community_members.add(communityResponse);

        }
        return community_members;
    }

    public ResponseEntity<String> addMovieToCommunity(int community_id, int movie_id, String username) {
        Community community = communityRepo.findCommunityById(community_id);
        Map<Integer, LikesEntry> movies = community.getMovies();
        if (movies.containsKey(movie_id)) {
            return new ResponseEntity<>("Movie already exists", HttpStatus.CONFLICT);
        }else{
            LikesEntry likesEntry = new LikesEntry();
            likesEntry.setAdded_by(username);
            Set<String> linked_user = new HashSet<>();
            likesEntry.setLinked_user(linked_user);
            movies.put(movie_id, likesEntry);
            community.setMovies(movies);

            communityRepo.save(community);
            return new ResponseEntity<>("Movie added successfully", HttpStatus.CREATED);
        }

    }

    public ResponseEntity<String> removeMovieFromCommunity(int communityId, int movieId) {
        Community community = communityRepo.findCommunityById(communityId);
        Map<Integer, LikesEntry> movies = community.getMovies();
        if (movies.containsKey(movieId)) {
            movies.remove(movieId);
            community.setMovies(movies);
            communityRepo.save(community);
            return new ResponseEntity<>("Movie removed successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
        }
    }

    public String addLikeTOMovie(int community_id, int movie_id, String username) {
        Community community = communityRepo.findCommunityById(community_id);
        Map<Integer, LikesEntry> movies = community.getMovies();
        if (movies.containsKey(movie_id)) {
            LikesEntry likesEntry = movies.get(movie_id);
            Set<String> linked_user = likesEntry.getLinked_user();
            if (!linked_user.contains(username)) {
                linked_user.add(username);
                likesEntry.setLinked_user(linked_user);
                community.setMovies(movies);
                communityRepo.save(community);
                return "Movie Liked successfully";
            }else {
                return "Movie already Liked";
            }
        }else{
            return "Movie not found";
        }
    }


    public String removeLikeFromCommunity(int community_id, int movie_id, String username) {
        Community community = communityRepo.findCommunityById(community_id);
        Map<Integer, LikesEntry> movies = community.getMovies();
        if (movies.containsKey(movie_id)) {
            LikesEntry likesEntry = movies.get(movie_id);
            Set<String> linked_user = likesEntry.getLinked_user();
            if (linked_user.contains(username)) {
                linked_user.remove(username);
                likesEntry.setLinked_user(linked_user);
                community.setMovies(movies);
                communityRepo.save(community);
                return "Movie Unliked successfully";
            }else {
                return "Movie not found";
            }
        }else{
            return "Movie not found";
        }
    }

}
