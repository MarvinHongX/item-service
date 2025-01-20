package hello.itemservice.domain.item;

import lombok.Getter;

public enum ItemType {
    BOOK("book"), FOOD("food"), ETC("etc");

    @Getter private final String key;

    ItemType(String key) {
        this.key = key;
    }
}
