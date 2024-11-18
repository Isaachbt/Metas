package com.to_do_list.Metas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.to_do_list.Metas.model.role.RoleUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleUser role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Tarefa> tarefaList;
    private boolean online;

    public User(UUID id, String email, String password, RoleUser role, List<Tarefa> tarefaList,boolean online) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tarefaList = tarefaList;
        this.online = online;
    }


    public User() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == RoleUser.ADMIN)
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
