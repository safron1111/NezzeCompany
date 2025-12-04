package net.safron1111.nezzeco.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.item.ModItems;

@SuppressWarnings("removal")
public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, NezzeCo.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.IRIDIUM);
        simpleItem(ModItems.IRIDIUM_NUGGET);
        simpleItem(ModItems.RAW_IRIDIUM);
        simpleItem(ModItems.CHEESE);
        simpleItem(ModItems.CHEESEBURGER);
        simpleItem(ModItems.RYE_SEEDS);
        simpleItem(ModItems.RYE);
        simpleItem(ModItems.ERGOT_RYE);
        simpleItem(ModItems.RYE_BREAD);
        simpleItem(ModItems.PUMPERNICKEL);
        simpleItem(ModItems.CRISPBREAD);
        simpleItem(ModItems.COARSE_RYE_FLOUR);
        simpleItem(ModItems.RYE_FLOUR);
        simpleItem(ModItems.COARSE_RYE_DOUGH);
        simpleItem(ModItems.RYE_DOUGH);

        toolItem(ModItems.IRIDIUM_SWORD);
        toolItem(ModItems.IRIDIUM_AXE);
        toolItem(ModItems.IRIDIUM_SHOVEL);
        toolItem(ModItems.IRIDIUM_PICKAXE);
        toolItem(ModItems.IRIDIUM_HOE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(NezzeCo.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder toolItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0"
        , new ResourceLocation(NezzeCo.MOD_ID, "item/" + item.getId().getPath()));
    }
}
