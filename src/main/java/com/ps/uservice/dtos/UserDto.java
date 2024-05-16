package com.ps.uservice.dtos;

import com.ps.uservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserDto {
    private String name;
    private String emailId;
    private Set<Role> roles;
    private UUID Id;
}
