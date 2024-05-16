package com.ps.uservice.dtos;

import com.ps.uservice.models.Role;
import com.ps.uservice.models.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {
    private String name;
    private String emailId;
    private String password;
    private List<RoleType> roleTypes;
}
