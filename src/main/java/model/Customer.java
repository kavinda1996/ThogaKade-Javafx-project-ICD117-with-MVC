package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customer {
    private String custID;
    private String custTitle;
    private String custName;
    private LocalDate DOB;
    private double salary;
    private String custAddress;
    private String city;
    private String province;
    private String postalCode;



}
