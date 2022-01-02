package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.PerformanceMonitor;

public interface MonitoringDao {

    PerformanceMonitor save(PerformanceMonitor performanceMonitor);
}
