package com.demo.batch.datapublisher.mapper;

import com.demo.batch.datapublisher.model.EmployeeDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDtoMapper implements RowMapper<EmployeeDto> {
    @Override
    public EmployeeDto mapRow(ResultSet rs, int i) throws SQLException {

        EmployeeDto employee = new EmployeeDto();
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setCity(rs.getString("city"));

        return employee;
    }
}
