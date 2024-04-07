package com.ps.uservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LogoutRequestDto {
    private UUID userId;
    private String token;
}
