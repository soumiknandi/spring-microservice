package com.soumik.apigateway;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JWTError {

    private String error;

}
