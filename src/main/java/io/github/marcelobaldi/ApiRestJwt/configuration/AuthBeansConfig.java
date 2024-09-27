package io.github.marcelobaldi.ApiRestJwt.configuration;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.NotFoundException;
import io.github.marcelobaldi.ApiRestJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration public class AuthBeansConfig {
    @Autowired private UserRepository userRepository;

    @Bean UserDetailsService userDetailsService() {
        return userEmail -> (UserDetails) userRepository
                .findOneByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
    }

    @Bean BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
            authProvider.setUserDetailsService(userDetailsService());
            authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

