package com.univercity.vntu.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestModel {

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String creator;
    private Date createdAt;
    private List<Question> questionList;

    public TestModel(ObjectId id, String name, String creator, List<Question> questionList) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.createdAt = new Date();
        this.questionList = questionList;
    }

    public TestModel setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public TestModel setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }


    private class Question {
        private String title;
        private Map<Integer, String> answers;
        private int rightAnswer;
        private int cost;
    }

    public int checkTest(List<Integer> answers) {
        int total = 0;
        if (answers.size() == questionList.size()) {
            for (int i = 0; i < answers.size(); i++) {
                Question question = questionList.get(i);
                if(question.rightAnswer == answers.get(i)){
                    total+=question.cost;
                }
            }
        }
        return total;
    }
}
