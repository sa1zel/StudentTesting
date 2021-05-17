package com.univercity.vntu.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestResult {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private ObjectId testId;
    private ObjectId studentId;
    private int result;
    private final Date passedAt = new Date();
    private List<Integer> answers;

    public TestResult(String testId, String studentId, int result, List<Integer> answers) {
        id = new ObjectId();
        this.testId = new ObjectId(testId);
        this.studentId = new ObjectId(studentId);
        this.result = result;
        this.answers = answers;
    }

    public TestResult setId(ObjectId id) {
        this.id = id;
        return this;
    }
}
