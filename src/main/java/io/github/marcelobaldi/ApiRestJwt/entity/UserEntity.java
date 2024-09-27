package io.github.marcelobaldi.ApiRestJwt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotBlank(message = "{name.not.blank}")
    @Size(min = 3,  message = "{name.min.size}")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Email(message = "{email.format}")
    @Size(max = 40, message = "{email.max.size}")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonProperty(value = "pass", access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6,  message = "{pass.min.size}")
    @Column(name = "password", unique = true)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public void setName(String name)     { this.name     = name.trim(); }
    public void setPassword(String pass) { this.password = pass.trim(); }
    public void setEmail(String email)   { this.email = email.trim().toLowerCase(); }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of();}
    @Override public String getUsername() { return email; }
    @Override public String getPassword() { return password; }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}
