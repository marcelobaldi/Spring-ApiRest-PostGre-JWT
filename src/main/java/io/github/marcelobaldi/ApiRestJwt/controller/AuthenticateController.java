package io.github.marcelobaldi.ApiRestJwt.controller;

import io.github.marcelobaldi.ApiRestJwt.dto.login.LoginRequestDTO;
import io.github.marcelobaldi.ApiRestJwt.dto.login.LoginResponseDTO;
import io.github.marcelobaldi.ApiRestJwt.entity.UserEntity;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.BusinessRulesException;
import io.github.marcelobaldi.ApiRestJwt.repository.UserRepository;
import io.github.marcelobaldi.ApiRestJwt.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/authenticate")
@RestController
public class AuthenticateController {
    @Autowired private UserRepository        userRepository;
    @Autowired private PasswordEncoder       passwordEncoder;
    @Autowired private JwtService            jwtService;
    @Autowired private AuthenticationManager authenticationManager;

    @GetMapping("cors")
    public Map<String, Object> testCors(){
        String msg = "TESTE DE CORS OK !!!";
        Map<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        return map;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity signup(@RequestBody @Valid UserEntity bodyUser, HttpServletResponse response){
        Optional<UserEntity> userEntityOpt = userRepository.findOneByEmail(bodyUser.getEmail());
        if(userEntityOpt.isPresent()){ throw new BusinessRulesException("Este email já existe."); }

        String encryptedPassword  = passwordEncoder.encode(bodyUser.getPassword());
        bodyUser.setPassword(encryptedPassword);

        return userRepository.save(bodyUser);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String pass  = loginRequestDTO.getPassword();

        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(email, pass) );

        UserEntity userEntity = userRepository.findOneByEmail(email).orElseThrow();
        String jwtToken = jwtService.generateToken(userEntity);

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(jwtToken, jwtService.getExpirationTime() );
        return ResponseEntity.ok(loginResponseDTO);
    }
}
