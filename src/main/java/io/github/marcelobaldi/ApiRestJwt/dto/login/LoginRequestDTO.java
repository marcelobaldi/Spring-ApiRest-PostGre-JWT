package io.github.marcelobaldi.ApiRestJwt.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;

    public void setEmail(String email)       { this.email = email.trim().toLowerCase(); }
    public void setPassword(String password) { this.password = password; }
}


