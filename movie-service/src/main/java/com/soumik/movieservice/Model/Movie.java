package com.soumik.movieservice.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String language;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movieId", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MovieArtist> artistIds;

}
