package tacos.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {
    
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        ingredients.add(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
        ingredients.add(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        ingredients.add(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
        ingredients.add(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
        ingredients.add(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
        ingredients.add(new Ingredient("CHED", "Cheddar", Type.CHEESE));
        ingredients.add(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
        ingredients.add(new Ingredient("SLSA", "Salsa", Type.SAUCE));
        ingredients.add(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
        
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(Taco taco, @ModelAttribute("tacoOrder") TacoOrder tacoOrder) {
        log.info("Processing design: " + taco);
        tacoOrder.addTaco(taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).toList();
    }
}
