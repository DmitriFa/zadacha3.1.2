package com.example.demoboot.entitiy;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users1")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "age")
    private Byte age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @JoinTable(
            name = "users1_roles"
            , joinColumns = {
            @JoinColumn(name = "users1_id")
    }
            , inverseJoinColumns = {
            @JoinColumn(name = "roles_id")
    }
    )
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String name, String lastName, Byte age, String email, String password, Set<Role> roles) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getRoleString() {
        StringBuilder sb = new StringBuilder();
        for(Role role :roles) {
            if (role.getName().equals("ROLE_USER")) {
              sb.append("USER ");
            }
            if (role.getName().equals("ROLE_ADMIN")) {
                sb.append("ADMIN ");
            }
                else sb.append("");
        }
        return sb.toString();
}
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return  auth.getName() + "with Roles";
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role>roles) {
        this.roles = roles;
    }

}
