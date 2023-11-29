package com.soumik.artistservice.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArtistRequest {

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String gender;


//    public static Artist getMovieModel(ArtistRequest artistRequest) {
//        return Artist.builder()
//                .firstName(artistRequest.firstName.trim())
//                .lastName(artistRequest.lastName.trim())
//                .gender(artistRequest.gender.trim())
//                .build();
//    }

}
