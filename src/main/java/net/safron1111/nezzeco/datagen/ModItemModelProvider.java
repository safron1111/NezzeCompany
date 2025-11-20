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
        simpleItem(ModItems.RAW_IRIDIUM);
        simpleItem(ModItems.CHEESE);
        simpleItem(ModItems.CHEESEBURGER);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(NezzeCo.MOD_ID, "item/" + item.getId().getPath()));
    }
}
