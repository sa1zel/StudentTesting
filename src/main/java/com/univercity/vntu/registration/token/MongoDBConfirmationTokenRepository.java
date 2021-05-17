package com.univercity.vntu.registration.token;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class MongoDBConfirmationTokenRepository implements ConfirmationTokenRepository {

    private final MongoClient client;
    private MongoCollection<ConfirmationToken> tokenCollection;

    public MongoDBConfirmationTokenRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        tokenCollection = client.getDatabase("QuizDB").getCollection("tokens", ConfirmationToken.class);
    }

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return Optional.ofNullable(tokenCollection.find(eq("token", token)).first());
    }

    @Override
    public long updateConfirmedAt(String token, LocalDateTime confirmedAt) {
        Bson filter = eq( "token", token);
        Bson updateOperation = set("confirmedAt", confirmedAt);
        UpdateResult result = tokenCollection.updateOne(filter, updateOperation);
        return result.getModifiedCount();
    }

    @Override
    public void save(ConfirmationToken token) {
        token.setId(new ObjectId());
        tokenCollection.insertOne(token);
    }
}
