package hello.itemservice.domain.item;

import lombok.Getter;

public enum ItemType {
    BOOK("book"), FOOD("food"), ETC("etc");

    @Getter private final String description;

    ItemType(String description) {
        this.description = description;
    }


}
