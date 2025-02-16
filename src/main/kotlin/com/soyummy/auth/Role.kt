package com.soyummy.auth

import org.springframework.security.core.GrantedAuthority

enum class Role : GrantedAuthority {
    ADMIN,
    USER;

    override fun getAuthority(): String {
        return "ROLE_$name"
    }
}