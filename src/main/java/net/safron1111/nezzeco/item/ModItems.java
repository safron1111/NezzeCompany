package net.safron1111.nezzeco.item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;
import net.safron1111.nezzeco.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NezzeCo.MOD_ID);

    public static final RegistryObject<Item> IRIDIUM = ITEMS.register("iridium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRIDIUM_NUGGET = ITEMS.register("iridium_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_IRIDIUM = ITEMS.register("raw_iridium",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHEESE = ITEMS.register("cheese",
            () -> new Item(new Item.Properties().food(ModFoods.CHEESE)));
    public static final RegistryObject<Item> CHEESEBURGER = ITEMS.register("cheeseburger",
            () -> new Item(new Item.Properties().food(ModFoods.CHEESEBURGER)));

    public static final RegistryObject<Item> RYE_BREAD = ITEMS.register("rye_bread",
            () -> new Item(new Item.Properties().food(ModFoods.RYE_BREAD)));
    public static final RegistryObject<Item> PUMPERNICKEL = ITEMS.register("pumpernickel",
            () -> new Item(new Item.Properties().food(ModFoods.PUMPERNICKEL)));
    public static final RegistryObject<Item> CRISPBREAD = ITEMS.register("crispbread",
            () -> new Item(new Item.Properties().food(ModFoods.CRISPBREAD)));

    public static final RegistryObject<Item> RYE = ITEMS.register("rye",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ERGOT_RYE = ITEMS.register("ergot_rye",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COARSE_RYE_FLOUR = ITEMS.register("coarse_rye_flour",
            () -> new CoarseRyeFlourItem(new Item.Properties()));
    public static final RegistryObject<Item> RYE_FLOUR = ITEMS.register("rye_flour",
            () -> new RyeFlourItem(new Item.Properties()));
    public static final RegistryObject<Item> RYE_DOUGH = ITEMS.register("rye_dough",
            () -> new RyeDoughItem(new Item.Properties().food(ModFoods.RYE_DOUGH)));
    public static final RegistryObject<Item> COARSE_RYE_DOUGH = ITEMS.register("coarse_rye_dough",
            () -> new CoarseRyeDoughItem(new Item.Properties().food(ModFoods.RYE_DOUGH)));

    public static final RegistryObject<Item> IRIDIUM_SWORD = ITEMS.register("iridium_sword",
            () -> new SwordItem(ModToolTiers.IRIDIUM,3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> IRIDIUM_AXE = ITEMS.register("iridium_axe",
            () -> new AxeItem(ModToolTiers.IRIDIUM,5, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> IRIDIUM_SHOVEL = ITEMS.register("iridium_shovel",
            () -> new ShovelItem(ModToolTiers.IRIDIUM,1.5f, -3.0f, new Item.Properties()));
    public static final RegistryObject<Item> IRIDIUM_PICKAXE = ITEMS.register("iridium_pickaxe",
            () -> new PickaxeItem(ModToolTiers.IRIDIUM,1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> IRIDIUM_HOE = ITEMS.register("iridium_hoe",
            () -> new HoeItem(ModToolTiers.IRIDIUM,-3, -0.0f, new Item.Properties()));

    public static final RegistryObject<Item> UBERBOW = ITEMS.register("uberbow",
            () -> new UberBowItem(new Item.Properties()));
    public static final RegistryObject<Item> ACCELERATOR = ITEMS.register("accelerator",
            () -> new AcceleratorItem(new Item.Properties()));

    //Seeds use ItemNameBlockItem to make lang easier
    public static final RegistryObject<Item> RYE_SEEDS = ITEMS.register("rye_seeds",
            () -> new ItemNameBlockItem(ModBlocks.RYE_CROP.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
