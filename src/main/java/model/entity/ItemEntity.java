package model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemEntity {


    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}
