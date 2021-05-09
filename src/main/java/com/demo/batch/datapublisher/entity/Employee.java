package com.demo.batch.datapublisher.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "testdb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee implements Serializable {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String city;

}
