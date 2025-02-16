package com.soyummy.subscribes

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "subscribes")
data class Subscribe(
    @Id
    val id: String? = null,

    @Field("owner")
    val ownerId: String,

    @Field("email")
    val email: String
)