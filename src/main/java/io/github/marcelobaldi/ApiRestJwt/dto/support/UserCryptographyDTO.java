package io.github.marcelobaldi.ApiRestJwt.dto.support;
import lombok.Data;

@Data
public class UserCryptographyDTO {
    private String passOriginal;
    private String passEncrypted;

    public UserCryptographyDTO(String passOriginal) {
        this.passOriginal = passOriginal;
    }

    public UserCryptographyDTO(String passOriginal, String passEncrypted) {
        this.passOriginal  = passOriginal;
        this.passEncrypted = passEncrypted;
    }
}
