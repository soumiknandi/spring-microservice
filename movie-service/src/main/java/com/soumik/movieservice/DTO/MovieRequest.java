package com.soumik.movieservice.DTO;

import com.soumik.movieservice.Model.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MovieRequest {

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be blank")
    private String fullName;

    @NotNull(message = "Language cannot be blank")
    @NotBlank(message = "Language cannot be blank")
    private String language;

    private List<Long> artistIds;


    public static Movie getMovieModel(MovieRequest movieRequest) {
        return Movie.builder()
                .fullName(movieRequest.getFullName().trim())
                .language(movieRequest.getLanguage().trim())
                .build();
    }
}
