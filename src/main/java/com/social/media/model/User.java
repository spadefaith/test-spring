package com.social.media.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        private String firstName;

        private String lastName;


        @OneToOne
        private Profile profile;

        @OneToMany
        private List<Post> posts = new ArrayList<>();


        @ManyToMany
        private Set<Group> groups = new HashSet<>();
}
