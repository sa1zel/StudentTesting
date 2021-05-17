package com.univercity.vntu.repositories;

import com.univercity.vntu.models.TestResult;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository {
    TestResult save(TestResult testResult);

    List<TestResult> findAll();

    List<TestResult> findAll(List<String> ids);

    List<TestResult> findByStudentId(String studentId);

    List<TestResult> findByTestId(String testId);

    TestResult findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();
}
