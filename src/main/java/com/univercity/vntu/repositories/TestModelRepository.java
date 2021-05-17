package com.univercity.vntu.repositories;

import com.univercity.vntu.models.TestModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestModelRepository {

    TestModel save(TestModel testModel);

    List<TestModel> findAll();

    List<TestModel> findAll(List<String> ids);

    TestModel findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    TestModel update(TestModel testModel);
}
