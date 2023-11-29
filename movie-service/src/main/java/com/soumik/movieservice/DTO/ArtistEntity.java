package com.soumik.movieservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistEntity {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;



}
