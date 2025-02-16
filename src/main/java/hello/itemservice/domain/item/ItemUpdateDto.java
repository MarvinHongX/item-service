package hello.itemservice.domain.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter @Setter
public class ItemUpdateDto {

    @NotNull
    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Min(1)
    private Integer quantity;

    private Boolean onSale;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public ItemUpdateDto() {
    }

}
