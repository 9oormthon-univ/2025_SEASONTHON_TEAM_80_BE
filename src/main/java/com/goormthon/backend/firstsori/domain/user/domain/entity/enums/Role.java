package com.goormthon.backend.firstsori.domain.user.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("USER"),
    ANONYMOUS("ANONYMOUS"),
    ADMIN("ADMIN");

    private final String role;

}
