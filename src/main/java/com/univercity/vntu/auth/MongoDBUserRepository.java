package com.univercity.vntu.auth;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Repository
public class MongoDBUserRepository implements ApplicationUserRepository {
    private final MongoClient client;
    private MongoCollection<ApplicationUser> userCollection;

    public MongoDBUserRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userCollection = client.getDatabase("QuizDB").getCollection("users", ApplicationUser.class);
    }


    @Override
    public Optional<ApplicationUser> findByEmail(String email) {
        return Optional.ofNullable(userCollection.find(eq("email", email)).first());
    }

    @Override
    public long enableAppUser(String email) {
        Bson filter = eq( "email", email);
        Bson updateOperation = set("enabled", true);
        UpdateResult result = userCollection.updateOne(filter, updateOperation);
        return result.getModifiedCount();
    }

    @Override
    public void save(ApplicationUser appUser) {
        appUser.setId(new ObjectId());
        userCollection.insertOne(appUser);
    }
}
