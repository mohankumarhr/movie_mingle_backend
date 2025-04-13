package com.movie_recomendation.movie_mingle.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private boolean emailVerified = false;

    @Getter @Setter
    @OneToMany(mappedBy = "owner")
    private Set<Community> ownedCommunities;

    @ManyToMany(mappedBy = "members")
    private Set<Community> memberCommunities;

}
