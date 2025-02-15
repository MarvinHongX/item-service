package hello.itemservice.web.item;

import hello.itemservice.domain.item.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder("itemParamDto")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(itemValidator);
    }

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", messageSource.getMessage("label.region.seoul", null, LocaleContextHolder.getLocale()));
        regions.put("BUSAN", messageSource.getMessage("label.region.busan", null, LocaleContextHolder.getLocale()));
        regions.put("JEJU", messageSource.getMessage("label.region.jeju", null, LocaleContextHolder.getLocale()));
        return regions;
    }

    @ModelAttribute("itemTypes")
    public List<Map<String, String>> itemTypes() {
        List<Map<String, String>> itemTypes = new ArrayList<>();

        Locale locale = LocaleContextHolder.getLocale();

        for (ItemType itemType : ItemType.values()) {
            Map<String, String> itemTypeMap = new LinkedHashMap<>();
            itemTypeMap.put("name", itemType.name());

            String description = messageSource.getMessage("label.itemType." + itemType.getKey(), null, locale);
            itemTypeMap.put("description", description);

            itemTypes.add(itemTypeMap);
        }

        return itemTypes;
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", messageSource.getMessage("label.shipping.fast", null, LocaleContextHolder.getLocale())));
        deliveryCodes.add(new DeliveryCode("NORMAL", messageSource.getMessage("label.shipping.normal", null, LocaleContextHolder.getLocale())));
        deliveryCodes.add(new DeliveryCode("SLOW", messageSource.getMessage("label.shipping.slow", null, LocaleContextHolder.getLocale())));
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
        Item testA = new Item("testA", 10000, 10);
        Item testB = new Item("testB", 20000, 20);

        List<String> regions = new ArrayList<>(Arrays.asList("SEOUL", "BUSAN"));

        ItemParamDto testADto = new ItemParamDto();
        testADto.setItemName(testA.getItemName());
        testADto.setPrice(testA.getPrice());
        testADto.setQuantity(testA.getQuantity());
        testADto.setOnSale(true);
        testADto.setRegions(regions);
        testADto.setItemType(ItemType.ETC);
        testADto.setDeliveryCode("NORMAL");
        testA.updateItem(testADto);

        ItemParamDto testBDto = new ItemParamDto();
        testBDto.setItemName(testB.getItemName());
        testBDto.setPrice(testB.getPrice());
        testBDto.setQuantity(testB.getQuantity());
        testBDto.setOnSale(false);
        testBDto.setRegions(regions);
        testBDto.setItemType(ItemType.FOOD);
        testBDto.setDeliveryCode("SLOW");
        testB.updateItem(testBDto);

        itemRepository.save(testA);
        itemRepository.save(testB);
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

//    @PostMapping("/add")
    public String addV7(ItemParamDto itemParamDto, RedirectAttributes redirectAttributes) {
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.updateItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

//    @PostMapping("/add")
    public String addV8(@ModelAttribute("item") ItemParamDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        // 특정 필드 검증 로직
        if (!StringUtils.hasText(itemParamDto.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", itemParamDto.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if (itemParamDto.getPrice() == null || itemParamDto.getPrice() < 1000 || itemParamDto.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", itemParamDto.getPrice(), false, new String[]{"range.item.price"}, new Object[]{"1,000", "1,000,000"}, null));
        }
        if (itemParamDto.getQuantity() == null || itemParamDto.getQuantity() < 1 || itemParamDto.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item", "quantity", itemParamDto.getQuantity(), false, new String[]{"range.item.quantity"}, new Object[]{"1", "10,000"}, null));
        }

        // 복합 룰 검증 로직
        if (itemParamDto.getPrice() != null && itemParamDto.getQuantity() != null) {
            int amount = itemParamDto.getPrice() * itemParamDto.getQuantity();
            if (amount < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"minAmount"}, new Object[]{"10,000", amount}, null));
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        // 정상 로직
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.updateItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

//    @PostMapping("/add")
    public String addV9(@ModelAttribute("item") ItemParamDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (itemValidator.supports(itemParamDto.getClass())) {
            itemValidator.validate(itemParamDto, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        // 정상 로직
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.updateItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

    @PostMapping("/add")
    public String addV10(@Validated @ModelAttribute("item") ItemParamDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addForm";
        }

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

