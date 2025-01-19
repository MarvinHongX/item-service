package hello.itemservice.web.item;

import hello.itemservice.domain.item.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "Seoul");
        regions.put("BUSAN", "Busan");
        regions.put("JEJU", "Jeju");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "Express Shipping"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "Standard Shipping"));
        deliveryCodes.add(new DeliveryCode("SLOW", "Economy Shipping"));
        return deliveryCodes;
    }
    @GetMapping("/")
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    /**
     * test data
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }

    @GetMapping("/item/{id}")
    public String item(@PathVariable Long id, Model model) {
        Item findItem = itemRepository.findById(id);
        model.addAttribute("item", findItem);
        return "item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new ItemParamDto());
        return "addForm";
    }

//    @PostMapping("/add")
    public String addV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model ) {
        Item item = new Item(itemName, price, quantity);
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item", savedItem);
        return "item";
    }

//    @PostMapping("/add")
    public String addV2(@ModelAttribute Item item, Model model) {
        Item savedItem = itemRepository.save(item);
        model.addAttribute("item", savedItem);
        return "item";
    }

//    @PostMapping("/add")
    public String addV3(@ModelAttribute("item") Item item) {
        itemRepository.save(item);
        return "item";
    }

//    @PostMapping("/add")
    public String addV4(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "item";
    }

//    @PostMapping("/add")
    public String addV5(Item item) {
        itemRepository.save(item);
        return "item";
    }

//    @PostMapping("/add")
    public String addV6(Item item) {
        itemRepository.save(item);
        return "redirect:/item/" + item.getId();
    }

    @PostMapping("/add")
    public String addV7(ItemParamDto itemParamDto, RedirectAttributes redirectAttributes) {
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.updateItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

    @GetMapping("/item/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Item findItem = itemRepository.findById(id);
        model.addAttribute("item", findItem);
        return "editForm";
    }

    @PostMapping("/item/{id}/edit")
    public String edit(@PathVariable Long id, @ModelAttribute ItemParamDto itemParamDto) {
        itemRepository.update(id, itemParamDto);
        return "redirect:/item/{id}";
    }
}

