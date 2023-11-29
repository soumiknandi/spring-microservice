package com.soumik.artistservice.DTO;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {

    private Long id;
    private String fullName;
    private String language;

}
