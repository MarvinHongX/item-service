package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemParamDto {
    private String itemName;
    private Integer price;
    private Integer quantity;

    public ItemParamDto() {
    }

    public ItemParamDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
