package com.univercity.vntu.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.univercity.vntu.models.TestModel;
import com.univercity.vntu.models.TestResult;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

@Repository
public class MongoDBTestResultsRepository implements TestResultRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;

    private MongoCollection<TestResult> testResultCollection;

    public MongoDBTestResultsRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        testResultCollection = client.getDatabase("QuizDB").getCollection("results", TestResult.class);
    }

    @Override
    public TestResult save(TestResult testResult) {
        testResult.setId(new ObjectId());
        testResultCollection.insertOne(testResult);
        return testResult;
    }

    @Override
    public List<TestResult> findAll() {
        return testResultCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<TestResult> findAll(List<String> ids) {
        return testResultCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }

    @Override
    public List<TestResult> findByStudentId(String studentId) {
        return testResultCollection.find(eq("studentId", new ObjectId(studentId))).into(new ArrayList<>());
    }

    @Override
    public List<TestResult> findByTestId(String testId) {
        return testResultCollection.find(eq("testId", new ObjectId(testId))).into(new ArrayList<>());
    }

    @Override
    public TestResult findOne(String id) {
        return testResultCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public long count() {
        return testResultCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return testResultCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> testResultCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> testResultCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).collect(Collectors.toList());
    }
}
