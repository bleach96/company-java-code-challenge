package com.mindex.challenge.data;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.catalina.startup.RealmRuleSet;
import org.springframework.beans.factory.annotation.Autowired;

import com.mindex.challenge.dao.EmployeeRepository;

public class ReportingStructure {
    private Employee employee;
    private int numberOfReports;
    @Autowired
    private EmployeeRepository employeeRepository;

    // takes employee ID, sets the employee to the employee specified by the ID,
    // sets default # of reports to 0
    public ReportingStructure(String employeeID) {
        employee = employeeRepository.findByEmployeeId(employeeID);
        numberOfReports = 0;
        generateReportingStructure();
    }

    // calculates number of reports by going through list of direct reports
    private void generateReportingStructure() {
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
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }
}
