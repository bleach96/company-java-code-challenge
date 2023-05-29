package com.mindex.challenge.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure read(String id) {
        Employee employee = employeeRepository.findByEmployeeId(id);
        int allReports = getAllReports(employee);
        ReportingStructure reportingStructure = new ReportingStructure(employee, allReports);

        return reportingStructure;
    }

    private int getAllReports(Employee employee) {
        int numberOfReports = 0;
        Set<String> givenEmployeeIds = new HashSet<>();
        List<Employee> reports = employee.getDirectReports();
        while (!reports.isEmpty()) {
            Employee report = reports.get(0);
            if (!givenEmployeeIds.contains(report.getEmployeeId())) {
                numberOfReports++;
                givenEmployeeIds.add(report.getEmployeeId());
                List<Employee> lowerLevelReports = employeeRepository.findByEmployeeId(report.getEmployeeId())
                        .getDirectReports();
                if (lowerLevelReports != null) {
                    reports.addAll(lowerLevelReports);
                }
            }
            reports.remove(0);
        }

        return numberOfReports;
    }

}
