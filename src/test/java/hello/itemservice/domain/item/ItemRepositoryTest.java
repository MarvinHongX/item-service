package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 5000, 5);
        itemRepository.save(item1);
        itemRepository.save(item2);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items).hasSize(2);
        assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        // given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        ItemUpdateDto updateParam = new ItemUpdateDto();
        updateParam.setItemName("itemB");
        updateParam.setPrice(5000);
        updateParam.setQuantity(5);
        itemRepository.update(updateParam);

        // then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}