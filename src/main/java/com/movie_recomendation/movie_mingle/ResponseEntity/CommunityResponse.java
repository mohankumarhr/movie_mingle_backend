package com.movie_recomendation.movie_mingle.ResponseEntity;


import com.movie_recomendation.movie_mingle.Model.LikesEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityResponse {
    private int id;
    private String name;
    private Map<Integer, LikesEntry> movies = new HashMap<>();
    private UserResponse owner;

}
