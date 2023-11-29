package com.soumik.authservice.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JWTResponse {

    private String token;

}
