package com.soyummy.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Value

import com.mongodb.MongoClientSettings
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoConfig {
    private val logger = LoggerFactory.getLogger(MongoConfig::class.java)

    @Value("\${spring.data.mongodb.host}")
    private lateinit var host: String

    @Value("\${spring.data.mongodb.port}")
    private var port: Int = 27017

    @Value("\${spring.data.mongodb.database}")
    private lateinit var database: String

    @Value("\${spring.data.mongodb.username}")
    private lateinit var username: String

    @Value("\${spring.data.mongodb.password}")
    private lateinit var password: String

    @Bean
    fun mongoClient(): MongoClient {
        val connectionString = ConnectionString(
            "mongodb://$username:$password@$host:$port/$database?authSource=admin"
        )

        val settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()

        return MongoClients.create(settings)
    }

    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
        logger.info("Creating MongoTemplate for database: $database")
        return MongoTemplate(mongoClient, database)
    }
}