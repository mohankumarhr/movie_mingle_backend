package com.movie_recomendation.movie_mingle.Controller;

import com.movie_recomendation.movie_mingle.Model.Community;
import com.movie_recomendation.movie_mingle.Service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @PostMapping("/create")
    public String createCommunity(@RequestParam String name, @RequestParam String ownerName) {
            return communityService.createCommunity(name, ownerName);
    }

    @PostMapping("/addmember")
    public String addMember(@RequestParam int communityId, @RequestParam String ownerName) {
        return communityService.addMemberToCommunity(communityId,ownerName);
    }

    @DeleteMapping("/removemember")
    public String removeMember(@RequestParam int communityId, @RequestParam String ownerName) {
        return communityService.removeMemberFromCommunity(communityId,ownerName);
    }

    @GetMapping("/getdetails")
    public Community getDetails(@RequestParam int communityId) {
        return communityService.getCommunity(communityId);
    }

}
