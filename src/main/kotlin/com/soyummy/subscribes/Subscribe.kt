package com.soyummy.subscribes

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed

@Document(collection = "subscribes")
@CompoundIndex(name = "owner_email_unique_idx", def = "{'owner': 1, 'email': 1}", unique = true)
data class Subscribe(
    @Id
    val id: String? = null,

    @Field("owner")
    @Indexed
    val ownerId: String,

    @Field("email")
    @Indexed
    val email: String
)