package com.mindex.challenge.data;

import java.math.BigDecimal;
import java.util.Date;

public class Compensation {
    private Employee employee;
    private BigDecimal salary;
    private Date date = new Date();

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Date getDate() {
        return date;
    }

}
