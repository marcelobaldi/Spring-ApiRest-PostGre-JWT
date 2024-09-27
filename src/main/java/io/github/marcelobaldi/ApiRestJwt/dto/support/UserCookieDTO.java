package io.github.marcelobaldi.ApiRestJwt.dto.support;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCookieDTO {
    private String id;
    private String token;
}
