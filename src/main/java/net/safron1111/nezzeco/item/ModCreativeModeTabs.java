package net.safron1111.nezzeco.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NezzeCo.MOD_ID);

    public static final RegistryObject<CreativeModeTab> NEZZECO_TAB = CREATIVE_MODE_TABS.register("nezzeco_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.IRIDIUM.get()))
                    .title(Component.translatable("creativetab.nezzeco_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_IRIDIUM.get());
                        output.accept(ModItems.IRIDIUM.get());
                        output.accept(ModItems.IRIDIUM_NUGGET.get());

                        output.accept(ModItems.IRIDIUM_SWORD.get());
                        output.accept(ModItems.IRIDIUM_AXE.get());
                        output.accept(ModItems.IRIDIUM_SHOVEL.get());
                        output.accept(ModItems.IRIDIUM_PICKAXE.get());
                        output.accept(ModItems.IRIDIUM_HOE.get());

                        output.accept(ModItems.CHEESE.get());
                        output.accept(ModItems.CHEESEBURGER.get());

                        output.accept(ModItems.RYE_SEEDS.get());
                        output.accept(ModItems.RYE.get());
                        output.accept(ModItems.ERGOT_RYE.get());
                        output.accept(ModItems.COARSE_RYE_FLOUR.get());
                        output.accept(ModItems.RYE_FLOUR.get());
                        output.accept(ModItems.COARSE_RYE_DOUGH.get());
                        output.accept(ModItems.RYE_DOUGH.get());
                        output.accept(ModItems.RYE_BREAD.get());
                        output.accept(ModItems.PUMPERNICKEL.get());
                        output.accept(ModItems.CRISPBREAD.get());
                        output.accept(ModBlocks.MILLSTONE.get());

                        output.accept(ModBlocks.IRIDIUM_BLOCK.get());
                        output.accept(ModBlocks.RAW_IRIDIUM_BLOCK.get());
                        output.accept(ModBlocks.IRIDIUM_STONE_ORE.get());
                        output.accept(ModBlocks.IRIDIUM_DEEPSLATE_ORE.get());

                        output.accept(ModBlocks.TONE_BLOCK.get());
                        output.accept(ModBlocks.TRAMPOLINE_BLOCK.get());
                        output.accept(ModBlocks.EXPONENTIAL_GEL.get());
                        output.accept(ModBlocks.REPULSION_GEL.get());
                        output.accept(ModBlocks.PROPULSION_GEL.get());
                        output.accept(ModBlocks.REVULSION_GEL.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
