package com.univercity.vntu.models;


import com.univercity.vntu.models.TestModel;
import com.univercity.vntu.models.TestResult;
import com.univercity.vntu.repositories.TestModelRepository;
import com.univercity.vntu.repositories.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestModelService {
    private final TestResultRepository testResultRepository;
    private final TestModelRepository testModelRepository;

    public TestModelService(TestResultRepository testResultRepository, TestModelRepository testModelRepository) {
        this.testResultRepository = testResultRepository;
        this.testModelRepository = testModelRepository;
    }

    public TestResult getResult(List<Integer> testAnswers, String testId, String studentId) {
        TestModel testModel = testModelRepository.findOne(testId);
        int result = testModel.checkTest(testAnswers);
        TestResult testResult = new TestResult(testId, studentId, result, testAnswers);
        testResultRepository.save(testResult);
        return testResult;
    }
}
