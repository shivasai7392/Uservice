package com.ps.uservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
public class User extends  BaseModel{
    private String name;
    private String emailId;
    @JsonIgnore
    private String password;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();
}
