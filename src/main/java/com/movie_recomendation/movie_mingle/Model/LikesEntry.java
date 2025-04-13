package com.movie_recomendation.movie_mingle.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikesEntry {
    String added_by;
    private Set<String> linked_user = new HashSet<>();
}
