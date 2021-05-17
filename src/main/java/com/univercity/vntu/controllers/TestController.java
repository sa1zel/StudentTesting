package com.univercity.vntu.controllers;


import com.univercity.vntu.models.TestModel;
import com.univercity.vntu.repositories.TestModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestResultController.class);
    private final TestModelRepository testModelRepository;

    public TestController(TestModelRepository testModelRepository) {
        this.testModelRepository = testModelRepository;
    }

    @PostMapping("test")
    @ResponseStatus(HttpStatus.CREATED)
    public TestModel postTestModel(@RequestBody TestModel testModel) {
        return testModelRepository.save(testModel);
    }


    @GetMapping("tests")
    public List<TestModel> getTestModels() {
        return testModelRepository.findAll();
    }

    @GetMapping("test/{id}")
    public ResponseEntity<TestModel> getTestModel(@PathVariable String id) {
        TestModel testModel = testModelRepository.findOne(id);
        if (testModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(testModel);
    }

    @GetMapping("tests/{ids}")
    public List<TestModel> getTestModels(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return testModelRepository.findAll(listIds);
    }

    @GetMapping("tests/count")
    public Long getCount() {
        return testModelRepository.count();
    }

    @DeleteMapping("test/{id}")
    public Long deleteTestModel(@PathVariable String id) {
        return testModelRepository.delete(id);
    }

    @DeleteMapping("tests/{ids}")
    public Long deleteTestModels(@PathVariable String ids) {
        List<String> listIds = asList(ids.split(","));
        return testModelRepository.delete(listIds);
    }

    @DeleteMapping("tests")
    public Long deleteTestModels() {
        return testModelRepository.deleteAll();
    }

    @PutMapping("test")
    public TestModel putTestModel(@RequestBody TestModel testModel) {
        return testModelRepository.update(testModel);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
