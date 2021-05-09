package com.demo.batch.datapublisher.processor;

import com.demo.batch.datapublisher.entity.Employee;
import com.demo.batch.datapublisher.model.EmployeeDto;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, EmployeeDto> {
    @Override
    public EmployeeDto process(Employee employee) throws Exception {
        final String firstName = employee.getFirstName().toUpperCase();
        final String lastName = employee.getLastName().toUpperCase();
        final String city = employee.getCity().toUpperCase();
        final EmployeeDto transformedEmployee = new EmployeeDto(firstName, lastName, city);
        System.out.println("Converting (" + employee + ") into (" + transformedEmployee + ")");
        return transformedEmployee;
    }
}
