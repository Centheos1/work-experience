package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.MonitoringDao;
import com.fitnessplayground.dao.domain.PerformanceMonitor;
import com.fitnessplayground.dao.repository.PerformanceMonitorRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MonitoringDaoImpl implements MonitoringDao {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringDaoImpl.class);

    @Autowired
    private PerformanceMonitorRepository performanceMonitorRepository;

    @Override
    public PerformanceMonitor save(PerformanceMonitor performanceMonitor) {

        try {
            performanceMonitor = performanceMonitorRepository.save(performanceMonitor);
        } catch (Exception ex) {
            String errorMsg = "Exception saving member: "+performanceMonitor.getId();
            logger.error("Exception saving Performance Monitoring: id = [{}] Error: [{}]", performanceMonitor.getId(), ex.getMessage());
            throw new DatabaseException(errorMsg, ex);
        }

        return performanceMonitor;
    }
}
