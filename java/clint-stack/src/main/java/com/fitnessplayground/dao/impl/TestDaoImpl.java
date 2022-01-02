package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.TestDao;
import com.fitnessplayground.dao.domain.Test;
import com.fitnessplayground.dao.domain.TestSubmissionData;
import com.fitnessplayground.dao.repository.TestDataRepository;
import com.fitnessplayground.dao.repository.TestRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestDaoImpl implements TestDao {

    private static final Logger logger = LoggerFactory.getLogger(TestDaoImpl.class);

    @Autowired
    TestRepository testRepository;

    @Autowired
    TestDataRepository testDataRepository;

    @Override
    public Test saveTest(Test test) {

        try {
            logger.info("About to save Test: [{}]", test.toString());
            test = testRepository.save(test);
            logger.info("Saved Test: [{}]", test.toString());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+test.toString();
            logger.error("Exception saving member: [{}] Error: [{}]", test.toString(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return test;
    }

    @Override
    public TestSubmissionData saveTestSubmissionData(TestSubmissionData data) {

        try {
            data = testDataRepository.save(data);
//            logger.info("Saved Test: [{}]", data.toString());
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+data.toString();
            logger.error("Exception saving member: [{}] Error: [{}]", data.toString(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }
        return data;
    }
}
