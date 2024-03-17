package com.ps.uservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends  BaseModel{
    private String name;
    private String emailId;
    private String password;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
