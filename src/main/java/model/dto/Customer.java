package model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

    private String CustomerID;
    private String CustomerTitle;
    private String customerName;
    private LocalDate dateOfBirth;
    private double salary;
    private String customerAddress;
    private String city;
    private String province;
    private String postalCode;

}
