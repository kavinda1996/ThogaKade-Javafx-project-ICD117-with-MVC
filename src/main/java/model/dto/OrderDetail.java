package model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {

    private String orderID;
    private String itemCode;
    private Integer orderQty;
    private double discount;
}
