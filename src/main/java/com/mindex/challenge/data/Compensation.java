package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private String salary;
    private Date date = new Date();

    public Compensation() {

    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getSalary() {
        return salary;
    }

    public Date getDate() {
        return date;
    }

}
