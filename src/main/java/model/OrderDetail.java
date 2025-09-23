package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDetail {
    private String orderID;
    private String itemCode;
    private Integer orderQTY;
    private Integer discount;
}
