package com.movie_recomendation.movie_mingle.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private int tmdb_id;
    private String title;
    private String url;
    @ManyToOne
    private Users user;
    @ElementCollection
    private Set<String> liked_users = new HashSet<>();

}
