package com.soumik.movieservice.DTO;

import com.soumik.movieservice.Model.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {

    private Long id;
    private String fullName;
    private String language;
    private List<ArtistEntity> artistIds;


    public static MovieResponse getMovieResponse(Movie movieMovie) {
        return MovieResponse.builder()
                .id(movieMovie.getId())
                .fullName(movieMovie.getFullName())
                .language(movieMovie.getLanguage())
                .artistIds(
                        movieMovie.getArtistIds().stream().map(
                            movieArtist ->
                                    ArtistEntity.builder()
                                                    .id(movieArtist.getArtistId())
                                                    .build()
                            ).toList()
                )
                .build();
    }


}
