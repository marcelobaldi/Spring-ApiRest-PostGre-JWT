package io.github.marcelobaldi.ApiRestJwt.controller;

import io.github.marcelobaldi.ApiRestJwt.entity.UserEntity;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.BusinessRulesException;
import io.github.marcelobaldi.ApiRestJwt.exception.exceptions.NotFoundException;
import io.github.marcelobaldi.ApiRestJwt.repository.UserRepository;
import io.github.marcelobaldi.ApiRestJwt.service.CryptographyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {
    @Autowired private UserRepository      userRepository;
    @Autowired private CryptographyService cryptographyService;
    @Autowired private PasswordEncoder     passwordEncoder;

    @PutMapping("update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserEntity update(@PathVariable Integer id, @RequestBody @Valid UserEntity bodyUser){
        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id não encontrado."));

        if(!userFound.getEmail().equals( bodyUser.getEmail() ) ){
           Boolean emailExists = userRepository.existsByEmail(bodyUser.getEmail());
           if (emailExists){
               throw new BusinessRulesException("Este email já existe.");
           }
        }

        String encryptedPassword = passwordEncoder.encode(bodyUser.getPassword());

        bodyUser.setId(id);
        bodyUser.setPassword(encryptedPassword);
        return userRepository.save(bodyUser);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
             userRepository.findById(id)
                .map( userFound -> {
                    userRepository.delete(userFound);
                    return userFound;
                }).orElseThrow( () -> new NotFoundException("Id não encontrado") );
    }

    @GetMapping("list")
    public List<UserEntity> findAll() {
        List<UserEntity> list = userRepository.findAll();
        if(list.isEmpty()){ throw new NotFoundException("Lista vazia."); }
        return list;
    }

    @GetMapping("{id}")
    public UserEntity findById(@PathVariable Integer id) {
        return userRepository
                .findById(id)
                .orElseThrow( ()-> new NotFoundException("Id não encontrado") );
    }

    public Optional<UserEntity> findExact(UserEntity data) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

        Example<UserEntity> example   = Example.of(data, matcher);
        Optional<UserEntity> optional = userRepository.findOne(example);
        return optional;
    }

    public List<UserEntity> findContain(UserEntity data) {
             ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<UserEntity> example = Example.of(data, matcher);
        return userRepository.findAll(example);
    }
}
