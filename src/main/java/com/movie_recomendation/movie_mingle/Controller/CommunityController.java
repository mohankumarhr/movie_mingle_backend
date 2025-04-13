package com.movie_recomendation.movie_mingle.Controller;

import com.movie_recomendation.movie_mingle.Repo.CommunityRepo;
import com.movie_recomendation.movie_mingle.Repo.UserRepo;
import com.movie_recomendation.movie_mingle.ResponseEntity.CommunityResponse;
import com.movie_recomendation.movie_mingle.ResponseEntity.UserResponse;
import com.movie_recomendation.movie_mingle.Service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityRepo communityRepo;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/create")
    public String createCommunity(@RequestParam String name, @RequestParam String ownerName) {
            return communityService.createCommunity(name, ownerName);
    }

    @PostMapping("/addmember")
    public ResponseEntity<String> addMember(@RequestParam int communityId, @RequestParam String userName) {
        return communityService.addMemberToCommunity(communityId,userName);
    }

    @DeleteMapping("/removemember")
    public String removeMember(@RequestParam int communityId, @RequestParam String ownerName) {
        return communityService.removeMemberFromCommunity(communityId,ownerName);
    }

    @PostMapping("/exitcommunity")
    public String exitCommunity(@RequestParam int communityId, @RequestParam String membername) {
        return communityService.exitFromCommunity(communityId, membername);
    }

    @GetMapping("/getdetails")
    public CommunityResponse getDetails(@RequestParam int communityId) {
        return communityService.getCommunity(communityId);
    }

    @GetMapping("/members")
    public List<UserResponse> getCommunityMembers(@RequestParam int id) {
        return communityService.getCommunityMembers(id);
    }

    @GetMapping("/communities")
    public List<CommunityResponse> getMemberedCommunity(@RequestParam int id) {
        return communityService.getMemberedCommunity(id);
    }

    @GetMapping("/ownedcommunities")
    public List<CommunityResponse> getOwnedCommunity(@RequestParam String name) {
        return communityService.getOwnedCommunity(name);
    }

    @DeleteMapping("/deletecommunity")
    public String deleteCommunity(@RequestParam int communityId) {
        return communityService.deleteCommunity(communityId);
    }

    @PostMapping("/addmovietoCommunity")
    public ResponseEntity<String> addMovieToCommunity(@RequestParam int communityId,
                                                      @RequestParam int movie_id,
                                                      @RequestParam String username) {
        return communityService.addMovieToCommunity(communityId, movie_id, username);
    }

    @PostMapping("/removemovie")
    public ResponseEntity<String> removeMovieFromCommunity(@RequestParam int communityId, @RequestParam int movie_id) {
        return communityService.removeMovieFromCommunity(communityId, movie_id);
    }

    @PostMapping("/likemovie")
    public String likeMovie(@RequestParam int communityId, @RequestParam int movie_id, @RequestParam String username) {
        return communityService.addLikeTOMovie(communityId, movie_id, username);
    }

    @PostMapping("/dislikemovie")
    public String dislikeMovie(@RequestParam int communityId, @RequestParam int movie_id, @RequestParam String username) {
        return communityService.removeLikeFromCommunity(communityId, movie_id, username);
    }
}
