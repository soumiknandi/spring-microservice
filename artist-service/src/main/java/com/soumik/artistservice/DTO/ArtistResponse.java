package com.soumik.artistservice.DTO;


import com.soumik.artistservice.Model.Artist;
import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;

    private List<MovieEntity> movies;

    public static ArtistResponse getArtistResponse(Artist artist){
        return ArtistResponse.builder()
                .id(artist.getId())
                .firstName(artist.getFirstName())
                .lastName(artist.getLastName())
                .gender(artist.getGender()).build();
    }

}
