package io.github.marcelobaldi.ApiRestJwt.service;
import io.github.marcelobaldi.ApiRestJwt.dto.support.UserCryptographyDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptographyService {
    private PasswordEncoder getBcrypt(){
        return new BCryptPasswordEncoder();
    }

    public String generate(UserCryptographyDTO user){
        return getBcrypt().encode(user.getPassOriginal());
    }

    public Boolean verify(UserCryptographyDTO user){
        return getBcrypt().matches(user.getPassOriginal(), user.getPassEncrypted());
    }
}
