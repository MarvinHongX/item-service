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
        List<String> regions = new ArrayList<>(Arrays.asList("SEOUL", "BUSAN"));

        ItemSaveDto testADto = new ItemSaveDto();
        testADto.setItemName("testA");
        testADto.setPrice(10000);
        testADto.setQuantity(10);
        testADto.setOnSale(true);
        testADto.setRegions(regions);
        testADto.setItemType(ItemType.ETC);
        testADto.setDeliveryCode("NORMAL");

        Item testA = new Item(testADto.getItemName(), testADto.getPrice(), testADto.getQuantity());
        testA.saveItem(testADto);
        itemRepository.save(testA);

        ItemSaveDto testBDto = new ItemSaveDto();
        testBDto.setItemName("testB");
        testBDto.setPrice(20000);
        testBDto.setQuantity(20);
        testBDto.setOnSale(false);
        testBDto.setRegions(regions);
        testBDto.setItemType(ItemType.FOOD);
        testBDto.setDeliveryCode("SLOW");

        Item testB = new Item(testBDto.getItemName(), testBDto.getPrice(), testBDto.getQuantity());
        testB.saveItem(testBDto);
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
        model.addAttribute("item", new ItemSaveDto());
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
    public String addV7(ItemSaveDto itemParamDto, RedirectAttributes redirectAttributes) {
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.saveItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

//    @PostMapping("/add")
    public String addV8(@ModelAttribute("item") ItemSaveDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
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
        objectReject(itemParamDto.getPrice(), itemParamDto.getQuantity(), bindingResult);

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        // 정상 로직
        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.saveItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

//    @PostMapping("/add")
    public String addV9(@ModelAttribute("item") ItemSaveDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (itemValidator.supports(itemParamDto.getClass())) {
            itemValidator.validate(itemParamDto, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.saveItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

    @PostMapping("/add")
    public String addV10(@Validated @ModelAttribute("item") ItemSaveDto itemParamDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        objectReject(itemParamDto.getPrice(), itemParamDto.getQuantity(), bindingResult);

        if (bindingResult.hasErrors()) {
            return "addForm";
        }

        Item item = new Item(itemParamDto.getItemName(), itemParamDto.getPrice(), itemParamDto.getQuantity());
        item.saveItem(itemParamDto);
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("id", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/item/{id}";
    }

    @GetMapping("/item/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Item findItem = itemRepository.findById(id);

        ItemUpdateDto itemParamDto = new ItemUpdateDto();
        itemParamDto.setId(findItem.getId());
        itemParamDto.setItemName(findItem.getItemName());
        itemParamDto.setPrice(findItem.getPrice());
        itemParamDto.setQuantity(findItem.getQuantity());
        itemParamDto.setOnSale(findItem.getOnSale());
        itemParamDto.setRegions(findItem.getRegions());
        itemParamDto.setItemType(findItem.getItemType());
        itemParamDto.setDeliveryCode(findItem.getDeliveryCode());

        model.addAttribute("item", itemParamDto);
        return "editForm";
    }

    @PostMapping("/item/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute("item") ItemUpdateDto itemParamDto, BindingResult bindingResult) {
        objectReject(itemParamDto.getPrice(), itemParamDto.getQuantity(), bindingResult);

        if (bindingResult.hasErrors()) {
            return "editForm";
        }

        itemRepository.update(itemParamDto);
        return "redirect:/item/{id}";
    }

    private static void objectReject(Integer itemParamDto, Integer itemParamDto1, BindingResult bindingResult) {
        if (itemParamDto != null && itemParamDto1 != null) {
            long amount = (long) itemParamDto * itemParamDto1;
            if (amount < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"minAmount"}, new Object[]{"10,000", amount}, null));
            }
        }
    }
}

