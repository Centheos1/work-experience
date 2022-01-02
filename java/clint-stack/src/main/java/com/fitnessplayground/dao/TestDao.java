package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.Test;
import com.fitnessplayground.dao.domain.TestSubmissionData;

public interface TestDao {
    Test saveTest(Test test);
    TestSubmissionData saveTestSubmissionData(TestSubmissionData data);
}
