package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

//used to using Mockito for service level testing 
//Duplicating code from Employee test to load data
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest {

    private String employeePostUrl;
    private String reportingStructureIdUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    Employee createdEmployee1;
    Employee createdEmployee2;
    Employee createdEmployee3;

    @Before
    public void setup() {
        employeePostUrl = "http://localhost:" + port + "/employee/";
        reportingStructureIdUrl = "http://localhost:" + port + "/reportingStructure/{id}";
        employee1 = createTestEmployee1();
        createdEmployee1 = restTemplate.postForEntity(employeePostUrl, employee1, Employee.class).getBody();
        employee2 = createTestEmployee2(employee1);
        createdEmployee2 = restTemplate.postForEntity(employeePostUrl, employee2, Employee.class).getBody();
        employee3 = createTestEmployee3(employee2);
        createdEmployee3 = restTemplate.postForEntity(employeePostUrl, employee3, Employee.class).getBody();
    }

    // not working due to issues with how it gets reporting structure from
    // RestTemplate
    @Test
    public void testReports() {
        ReportingStructure testReportingStructure1 = restTemplate
                .getForEntity(reportingStructureIdUrl, ReportingStructure.class, createdEmployee1.getEmployeeId())
                .getBody();
        assertEquals(testReportingStructure1.getEmployee(), employee1);
        assertEquals(testReportingStructure1.getNumberOfReports(), 0);

        ReportingStructure testReportingStructure3 = restTemplate
                .getForEntity(reportingStructureIdUrl, ReportingStructure.class, createdEmployee1.getEmployeeId())
                .getBody();
        assertEquals(testReportingStructure3.getEmployee(), employee3);
        assertEquals(testReportingStructure3.getNumberOfReports(), 2);
    }

    private Employee createTestEmployee1() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        return testEmployee;
    }

    private Employee createTestEmployee2(Employee employee1) {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("Jane");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer 2");
        List<Employee> reports = new ArrayList<Employee>();
        reports.add(employee1);
        testEmployee.setDirectReports(reports);
        return testEmployee;
    }

    private Employee createTestEmployee3(Employee employee2) {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Smith");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer 3");
        List<Employee> reports = new ArrayList<Employee>();
        reports.add(employee2);
        testEmployee.setDirectReports(reports);
        return testEmployee;
    }
}
