package com.soumik.movieservice.DTO;

import com.soumik.movieservice.Model.Movie;
import com.soumik.movieservice.Model.MovieArtist;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MovieWithoutArtistResponse {

    private Long id;
    private String fullName;
    private String language;

    public static List<MovieWithoutArtistResponse> getMovieWithoutArtistList(List<MovieArtist> movieArtists) {

        return movieArtists.stream().map(
                movieArtist -> {
                    Movie movie = movieArtist.getMovieId();
                    return MovieWithoutArtistResponse.builder()
                            .id(movie.getId())
                            .fullName(movie.getFullName())
                            .language(movie.getLanguage())
                            .build();
                }
        ).toList();

    }
}
