package hello.itemservice.validation;

import hello.itemservice.domain.item.ItemSaveDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

class BeanValidationTest {
    
    @Test
    void beanValidation() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        ItemSaveDto itemParamDto = new ItemSaveDto();
        itemParamDto.setItemName(" ");
        itemParamDto.setPrice(0);
        itemParamDto.setQuantity(10001);

        Set<ConstraintViolation<ItemSaveDto>> violations = validator.validate(itemParamDto);
        for (ConstraintViolation<ItemSaveDto> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation.getMessage() = " + violation.getMessage());
        }
    }
}
