package com.marvic.popstats.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class MongoConfig : AbstractMongoClientConfiguration() {
    override fun mongoClient(): MongoClient = MongoClients.create("mongodb://localhost:27017")

    override fun getDatabaseName() = "census"

    override fun getMappingBasePackages() = listOf("com.marvic.popstats.domain")
}