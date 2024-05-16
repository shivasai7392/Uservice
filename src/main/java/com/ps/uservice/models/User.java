package com.ps.uservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.collection.spi.PersistentSet;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@JsonDeserialize(as = User.class)
public class User extends  BaseModel{
    private String name;
    private String emailId;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = jakarta.persistence.FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
