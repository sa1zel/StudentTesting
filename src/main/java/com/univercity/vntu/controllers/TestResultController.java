package com.univercity.vntu.controllers;

import com.univercity.vntu.models.TestResult;
import com.univercity.vntu.repositories.TestResultRepository;
import com.univercity.vntu.models.TestModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api/tests/results")
public class TestResultController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestResultController.class);
    private final TestResultRepository testResultRepository;
    private final TestModelService testModelService;

    public TestResultController(TestResultRepository testResultRepository, TestModelService testModelService) {
        this.testResultRepository = testResultRepository;
        this.testModelService = testModelService;
    }

    @PostMapping("result")
    @ResponseStatus(HttpStatus.CREATED)
    public TestResult postTestResultByAnswers(@RequestBody List<Integer> testAnswers, @RequestBody String id) {
        String studentId = "228";

        return testModelService.getResult(testAnswers, id, studentId);
    }

//    @PostMapping("result")
//    @ResponseStatus(HttpStatus.CREATED)
//    public TestResult postGetTestResultsByStudentId(@RequestBody TestResult testResult) {
//        return testResultRepository.save(testResult);
//    }

    @GetMapping("result/{id}")
    public ResponseEntity<TestResult> getTestResult(@PathVariable String id) {
        TestResult testResult = testResultRepository.findOne(id);
        if (testResult == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(testResult);
    }

    @GetMapping("tests/count")
    public Long getCount() {
        return testResultRepository.count();
    }

    @DeleteMapping("test/{id}")
    public Long deleteTestModel(@PathVariable String id) {
        return testResultRepository.delete(id);
    }

    @DeleteMapping("tests/{ids}")
    public Long deleteTestModels(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return testResultRepository.delete(listIds);
    }

    @DeleteMapping("tests")
    public Long deleteTestModels() {
        return testResultRepository.deleteAll();
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
