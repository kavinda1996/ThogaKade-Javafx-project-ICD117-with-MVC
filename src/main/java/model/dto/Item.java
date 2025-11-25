package model.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Item {

    @Id
    private String itemCode;
    private String description;
    private String packSize;
    private Double unitPrice;
    private Integer qtyOnHand;
}
