package hello.itemservice.web.item;

import hello.itemservice.domain.item.ItemSaveDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemSaveDto.class.isAssignableFrom(clazz);  // Item이 아닌 ItemParamDto만 지원
    }

    @Override
    public void validate(Object target, Errors errors) {
        ItemSaveDto itemParamDto = (ItemSaveDto) target;

        // 특정 필드 검증 로직
        if (!StringUtils.hasText(itemParamDto.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if (itemParamDto.getPrice() == null || itemParamDto.getPrice() < 1000 || itemParamDto.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{"1,000", "1,000,000"}, null);
        }
        if (itemParamDto.getQuantity() == null || itemParamDto.getQuantity() < 1 || itemParamDto.getQuantity() > 10000) {
            errors.rejectValue("quantity", "range", new Object[]{"1", "10,000"}, null);
        }

        // 복합 룰 검증 로직
        if (itemParamDto.getPrice() != null && itemParamDto.getQuantity() != null) {
            int amount = itemParamDto.getPrice() * itemParamDto.getQuantity();
            if (amount < 10000) {
                errors.reject("minAmount", new Object[]{"10,000", amount}, null);
            }
        }

    }
}
