package com.ps.uservice.dtos;

import com.ps.uservice.models.RoleType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SetUserRolesRequestDto {
    private UUID userId;
    private List<RoleType> roleTypes;
}
