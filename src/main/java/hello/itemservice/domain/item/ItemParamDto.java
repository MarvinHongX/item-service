package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemParamDto {
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean onSale;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public ItemParamDto() {
    }

}
