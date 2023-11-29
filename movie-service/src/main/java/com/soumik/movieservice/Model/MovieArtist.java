package com.soumik.movieservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "movie_artist")
public class MovieArtist {

    @GeneratedValue
    @Id
    private Long id;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movieId;

    private Long artistId;

}
