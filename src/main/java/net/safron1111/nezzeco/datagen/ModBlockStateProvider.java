package net.safron1111.nezzeco.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.safron1111.nezzeco.NezzeCo;
import net.safron1111.nezzeco.block.ModBlocks;
import net.safron1111.nezzeco.block.custom.RyeCropBlock;

import java.util.function.Function;

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
        blockCubeBottomTopWithItem(ModBlocks.ARROW_TNT_BLOCK);
        blockCubeBottomTopWithItem(ModBlocks.TATB_BLOCK);

        makeRyeCrop((CropBlock) ModBlocks.RYE_CROP.get(), "rye_stage", "rye_stage");
        horizontalBlockWithItem(ModBlocks.MILLSTONE.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/millstone")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    //Thanks to Kaupenjoe
    public void makeRyeCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> ryeStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    //Thanks to Kaupenjoe
    private ConfiguredModel[] ryeStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RyeCropBlock) block).getAgeProperty()),
                new ResourceLocation(NezzeCo.MOD_ID, "block/" + textureName + state.getValue(((RyeCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
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

    public void horizontalBlockWithItem(Block block, ModelFile model) {
        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }
}
