package com.mindex.challenge.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    CompensationRepository compensationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Compensation create(Compensation compensation) {
        Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + compensation.getEmployee().getEmployeeId());
        }

        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String employeeId) {

        Compensation compensation = compensationRepository
                .findCompensationByEmployee(employeeRepository.findByEmployeeId(employeeId));

        if (compensation == null) {
            throw new RuntimeException("No compensation data found for employeeId: " + employeeId);
        }

        return compensation;
    }
}
