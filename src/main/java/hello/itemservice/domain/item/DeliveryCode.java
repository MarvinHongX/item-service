package hello.itemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * FAST: Express Shipping
 * NORMAL: Standard Shipping
 * SLOW: Economy Shipping
 */
@Getter @Setter
@AllArgsConstructor
public class DeliveryCode {
    private String code;
    private String displayName;
}