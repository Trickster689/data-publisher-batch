package com.demo.batch.datapublisher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto implements Serializable {
    private String firstName;
    private String lastName;
    private String city;

    public EmployeeDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
