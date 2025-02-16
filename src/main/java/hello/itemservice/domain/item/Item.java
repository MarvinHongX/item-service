package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class Item {
    @Setter private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean onSale;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public void saveItem(ItemSaveDto itemParamDto) {
        if (itemParamDto.getItemName() != null) {
            this.itemName = itemParamDto.getItemName();
        }
        if (itemParamDto.getPrice() != null) {
            this.price = itemParamDto.getPrice();
        }
        if (itemParamDto.getQuantity() != null) {
            this.quantity = itemParamDto.getQuantity();
        }
        if (itemParamDto.getOnSale() != null) {
            this.onSale = itemParamDto.getOnSale();
        }
        if (itemParamDto.getRegions() != null) {
            this.regions = itemParamDto.getRegions();
        }
        if (itemParamDto.getItemType() != null) {
            this.itemType = itemParamDto.getItemType();
        }
        if (itemParamDto.getDeliveryCode() != null) {
            this.deliveryCode = itemParamDto.getDeliveryCode();
        }
    }

    public void updateItem(ItemUpdateDto itemParamDto) {
        if (itemParamDto.getItemName() != null) {
            this.itemName = itemParamDto.getItemName();
        }
        if (itemParamDto.getPrice() != null) {
            this.price = itemParamDto.getPrice();
        }
        if (itemParamDto.getQuantity() != null) {
            this.quantity = itemParamDto.getQuantity();
        }
        if (itemParamDto.getOnSale() != null) {
            this.onSale = itemParamDto.getOnSale();
        }
        if (itemParamDto.getRegions() != null) {
            this.regions = itemParamDto.getRegions();
        }
        if (itemParamDto.getItemType() != null) {
            this.itemType = itemParamDto.getItemType();
        }
        if (itemParamDto.getDeliveryCode() != null) {
            this.deliveryCode = itemParamDto.getDeliveryCode();
        }
    }
}