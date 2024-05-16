package com.ps.uservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "roles")
@Getter
@Setter
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel{
    @Enumerated(EnumType.ORDINAL)
    private RoleType roleType;
}
