package net.safron1111.nezzeco.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties CHEESE = new FoodProperties.Builder()
            .meat()
            .nutrition(6)
            .saturationMod(0.7F)
            .build();
    public static final FoodProperties CHEESEBURGER = new FoodProperties.Builder()
            .meat()
            .nutrition(9)
            .saturationMod(1.2F)
            .build();

    public static final FoodProperties RYE_BREAD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(0.7F)
            .build();
    public static final FoodProperties PUMPERNICKEL = new FoodProperties.Builder()
            .nutrition(7)
            .saturationMod(0.8F)
            .build();
    public static final FoodProperties CRISPBREAD = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(0.6F)
            .build();
    public static final FoodProperties RYE_DOUGH = new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1F)
            .alwaysEat()
            .build();
}
