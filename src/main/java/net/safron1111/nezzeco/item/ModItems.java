package net.safron1111.nezzeco.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NezzeCo.MOD_ID);

    public static final RegistryObject<Item> IRIDIUM = ITEMS.register("iridium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_IRIDIUM = ITEMS.register("raw_iridium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().food(ModFoods.CHEESE)));
    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties().food(ModFoods.CHEESEBURGER)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
