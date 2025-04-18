package com.movie_recomendation.movie_mingle.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ElementCollection
    private Map<Integer, LikesEntry> movies = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;
    
    @ManyToMany
    @JoinTable
    private Set<Users> members;
}
