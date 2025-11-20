package net.safron1111.nezzeco.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;

@SuppressWarnings("removal")
public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, NezzeCo.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.IRIDIUM_BLOCK);
        blockWithItem(ModBlocks.RAW_IRIDIUM_BLOCK);

        blockWithItem(ModBlocks.IRIDIUM_STONE_ORE);
        blockWithItem(ModBlocks.IRIDIUM_DEEPSLATE_ORE);

        blockWithItem(ModBlocks.TONE_BLOCK);
        blockWithItem(ModBlocks.PROPULSION_GEL);
        blockWithItem(ModBlocks.REPULSION_GEL);
        blockWithItem(ModBlocks.EXPONENTIAL_GEL);
        blockWithItem(ModBlocks.REVULSION_GEL);

        blockCubeBottomTopWithItem(ModBlocks.TRAMPOLINE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockCubeBottomTopWithItem(RegistryObject<Block> blockRegistryObject) {
        String namespace = blockRegistryObject.getId().getNamespace() + ":";
        String name = blockRegistryObject.getId().toString()
                .replace(namespace,"");
        ResourceLocation side = new ResourceLocation(namespace + "block/" + name + "_side");
        ResourceLocation bottom = new ResourceLocation(namespace + "block/" + name + "_bottom");
        ResourceLocation top = new ResourceLocation(namespace + "block/" + name + "_top");
        simpleBlockWithItem(blockRegistryObject.get(),
                models().cubeBottomTop(name, side, bottom, top));
    }
}
