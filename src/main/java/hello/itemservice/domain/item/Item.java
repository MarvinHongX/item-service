package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Item {
    @Setter private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateItem(ItemParamDto itemParamDto) {
        if (itemParamDto.getItemName() != null) {
            this.itemName = itemParamDto.getItemName();
        }
        if (itemParamDto.getPrice() != null) {
            this.price = itemParamDto.getPrice();
        }
        if (itemParamDto.getQuantity() != null) {
            this.quantity = itemParamDto.getQuantity();
        }
    }
}