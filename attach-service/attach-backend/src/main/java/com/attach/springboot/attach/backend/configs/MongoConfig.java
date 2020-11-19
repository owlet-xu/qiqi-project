package com.attach.springboot.attach.backend.configs;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;

@Configuration
public class MongoConfig {

    @Bean
    public GridFSBucket gridFSBucket(MongoDbFactory mongoDbFactory) {
        MongoDatabase database = mongoDbFactory.getDb();
        return GridFSBuckets.create(database);
    }
}
