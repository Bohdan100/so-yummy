package com.soyummy.auth

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant

@Document(collection = "owners")
data class User(
    @Id
    val id: String? = null,

    @Field("name")
    val name: String,

    @Field("email")
    val email: String,

    @get:JvmName("user_password")
    @Field("password")
    val password: String,

    @Field("token")
    var token: String? = null,

    @Field("role")
    val role: Role,

    @CreatedDate
    @Field("createdAt")
    val createdAt: Instant? = null,

    @LastModifiedDate
    @Field("updatedAt")
    val updatedAt: Instant? = null
) : UserDetails {

    @JsonIgnore
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.authority))
    }

    @JsonIgnore
    override fun getUsername(): String {
        return email
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }
}