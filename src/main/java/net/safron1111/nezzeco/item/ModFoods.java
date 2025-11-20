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
}
