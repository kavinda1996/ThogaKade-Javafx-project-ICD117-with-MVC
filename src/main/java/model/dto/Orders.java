package model.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    
    private String OrderId;
    private LocalDate orderDate;
    private String customerId;
    
}
